package com.example

import akka.actor.ActorRef
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.RouteResult.Rejected
import akka.http.scaladsl.server.{AuthorizationFailedRejection, MethodRejection}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.addisonglobal.UserRoutes
import com.addisonglobal.actors.UserValidatorActor
import com.addisonglobal.model.RequestModel.Credentials
import com.addisonglobal.util.UserUtil
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}

class UserRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest
  with UserRoutes {

  override val userValidatorActor: ActorRef =
  system.actorOf(UserValidatorActor.props, "userValidator")

  lazy val routes = userRoutes

  "UserRoutes" should {
    "be able to validate user (POST /issuetoken)" in {
      val user = Credentials("suvankar", "SUVANKAR")
      val userEntity = Marshal(user).to[MessageEntity].futureValue

      val request = Post("/issuetoken").withEntity(userEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.OK)
        entityAs[String] should ===("suvankar_".concat(s"${UserUtil.getCurrentUtcTimestamp}"))
      }
    }

    "be able to reject the call if invalid credentials (POST /issuetoken) with AuthorizationFailedRejection" in {
      val user = Credentials("suvankar", "suvankar")
      val userEntity = Marshal(user).to[MessageEntity].futureValue

      val request = Post("/issuetoken").withEntity(userEntity)

      request ~> routes ~> check {
        rejection should === (AuthorizationFailedRejection)
      }
    }

    "be able to reject the call if the resource is not found (POST /issuetoken)" in {
      val request = Post("/validate")

      request ~> routes ~> check {
        Rejected
      }
    }

    "be able to reject the call if invalid HTTP method (POST /issuetoken) with MethodRejection" in {

      val request = Get("/issuetoken")

      request ~> routes ~> check {
        rejection should === (MethodRejection(HttpMethods.POST))
      }
    }
  }

}

