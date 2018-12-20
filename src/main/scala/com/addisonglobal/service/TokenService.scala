package com.addisonglobal.service

import com.addisonglobal.model.RequestModel.{Credentials, User, UserToken}

trait TokenService {

  /*
   * Refer to TokenServiceImpl for the implementation
   */
  protected def authenticate(credentials: Credentials): User

  /*
   * Refer to TokenServiceImpl for the implementation
   */
  protected def issueToken(user: User): UserToken

}
