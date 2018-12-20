package com.addisonglobal.bootstrap

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.addisonglobal.actors.UserValidatorActor
import com.addisonglobal.UserRoutes
import com.addisonglobal.constants.UserConstants

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object QuickstartServer extends App with UserRoutes {

  implicit val system: ActorSystem = ActorSystem(UserConstants.customActorSystem)
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val userValidatorActor: ActorRef = system.actorOf(UserValidatorActor.props, UserConstants.actorName)

  lazy val routes: Route = userRoutes
  Http().bindAndHandle(routes, UserConstants.host, UserConstants.port)

  println("Server online at http://".concat(s"${UserConstants.host}".concat(":").concat(s"${UserConstants.port}")))

  Await.result(system.whenTerminated, Duration.Inf)

}
