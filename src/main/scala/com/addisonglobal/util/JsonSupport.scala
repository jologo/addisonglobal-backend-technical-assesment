package com.addisonglobal.util

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.addisonglobal.model.RequestModel.{Credentials, User, UserToken}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonSupport extends SprayJsonSupport {

  import DefaultJsonProtocol._

  implicit val credentials: RootJsonFormat[Credentials] = jsonFormat2(Credentials)
  implicit val personToken = jsonFormat1(UserToken)
  implicit val person = jsonFormat1(User)
}
