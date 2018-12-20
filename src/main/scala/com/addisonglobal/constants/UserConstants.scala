package com.addisonglobal.constants

trait UserConstants

object UserConstants {
  final val host = "localhost"
  final val port = 9090
  final val actorName = "userValidatorActor"

  final val issueToken_path = "issuetoken"

  final val customActorSystem = "AkkaHttpServer"

  final val datePattern = "yyyy-MM-dd'T'HH:mm:ssZ"

  final val timeZone = "Etc/UTC"

  final val userIdValidationChar = "A"

  final val resourceNotFoundMsg = "The resource you are trying to access is not found"
  final val invalidCredsMsg = "Invalid credentials"
  final val invalidUserIdMsg = "Invalid userId"
}
