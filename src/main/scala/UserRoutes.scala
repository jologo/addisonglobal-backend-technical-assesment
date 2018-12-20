package com.addisonglobal

import akka.actor.{ActorRef, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives.post
import akka.http.scaladsl.server.directives.PathDirectives.path
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.{AuthorizationFailedRejection, RejectionHandler, Route, _}
import akka.pattern.ask
import akka.util.Timeout
import com.addisonglobal.actors.UserValidatorActor.IssueToken
import com.addisonglobal.constants.UserConstants
import com.addisonglobal.model.RequestModel.{Credentials, UserToken}
import com.addisonglobal.util.JsonSupport

import scala.concurrent.Future
import scala.concurrent.duration._

trait UserRoutes extends JsonSupport {
  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[UserRoutes])

  def userValidatorActor: ActorRef

  implicit lazy val timeout: Timeout = Timeout(5.seconds)

  /*
   * The routes to accept incoming http call
   * It handles an incoming Http Post call
   * URL - http://<host>:<port>/issuetoken
   *
   * @accept Credentials as request body
   */
  lazy val userRoutes: Route =
    path(UserConstants.issueToken_path) {
      post {
        entity(as[Credentials]) { credentials =>
          if (validateCredentials(credentials)) {
            val userValidated: Future[UserToken] = (userValidatorActor ? IssueToken(credentials)).mapTo[UserToken]
            onSuccess(userValidated) { user =>
              log.info("User validated successfully [{}] : {} ", credentials.username)
              complete((StatusCodes.OK, user.token))
            }
          } else {
            reject(AuthorizationFailedRejection)
          }
        }
      }
    }

  /*
   * This method validates the credentials provided by the user
   * and in turn return a boolean i.e. validation successful or not
   *
   * @param Credentials
   * @return Boolean          if validation successful
   */
  def validateCredentials(credentials: Credentials): Boolean = {
    var isValidCreds = false;
    if (!credentials.username.isEmpty && !credentials.password.isEmpty
      && !credentials.username.startsWith(UserConstants.userIdValidationChar)
      && credentials.username.toUpperCase.equals(credentials.password)) {
      isValidCreds = true
    }
    isValidCreds
  }

  /**
  *  Rejection handler to handle negative scenarios i.e.
  *  Authorization Failed Exception, Not Found, Method Rejection and Validation Rejection
  **/
  implicit def myRejectionHandler =
    RejectionHandler.newBuilder()
      .handle {
        case AuthorizationFailedRejection =>
          complete((Unauthorized, UserConstants.invalidCredsMsg))
      }
      .handle {
        case ValidationRejection(msg, _) =>
          complete((BadRequest, msg))
      }
      .handleAll[MethodRejection] { methodRejections =>
      val names = methodRejections.map(_.supported.name)
      complete((MethodNotAllowed, s"Can't do that! Supported: ${names mkString " or "}!"))
    }
      .handleNotFound {
        complete((NotFound, UserConstants.resourceNotFoundMsg))
      }
      .result()

}
