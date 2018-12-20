package com.addisonglobal.service

import com.addisonglobal.model.RequestModel.{Credentials, UserToken}

/*
 * Generates the user token
 */
object SimpleAsyncTokenService {
  def requestToken(credentials: Credentials): UserToken = TokenServiceImpl.requestToken(credentials)
}


