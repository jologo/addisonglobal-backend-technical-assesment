package com.addisonglobal.actors

import akka.actor.{Actor, ActorLogging, Props}
import com.addisonglobal.model.RequestModel.Credentials
import com.addisonglobal.service.SimpleAsyncTokenService

object UserValidatorActor {
  final case class IssueToken(credentials: Credentials)

  def props: Props = Props[UserValidatorActor]
}

class UserValidatorActor extends Actor with ActorLogging {
  import UserValidatorActor._

  /*
   * The receive method which will be invoked through message passing.
   * Currently handles only one message i.e. credentials and delegates the call
   * to the service class for implementation
   */
  def receive: Receive = {
    case IssueToken(credentials) =>
      log.info("Inside the actor - Generating credentials token [{}] {} ", credentials.username)
      sender() ! SimpleAsyncTokenService.requestToken(credentials)
  }
}
