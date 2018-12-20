package com.addisonglobal.model

object RequestModel {

  case class Credentials(username: String, password: String)
  case class User(userId: String)
  case class UserToken(token: String)
}
