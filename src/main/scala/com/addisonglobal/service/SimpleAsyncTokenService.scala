package com.addisonglobal.service

import com.addisonglobal.model.RequestModel.{Credentials, UserToken}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

trait SimpleAsyncTokenService {

  implicit val executionContext: ExecutionContextExecutor = ExecutionContext.global

  def requestToken(credentials: Credentials): UserToken = ???

}

/*
 * Generates the user token
 */
object SimpleAsyncTokenService {
  def requestToken(credentials: Credentials): UserToken = TokenServiceImpl.requestToken(credentials)
}


