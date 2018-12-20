package com.addisonglobal.service

import com.addisonglobal.model.RequestModel.{Credentials, User, UserToken}
import com.addisonglobal.util.UserUtil

object TokenServiceImpl extends TokenService {

  /*
   * Returns the User object containing the userId where userId = credentials.username
   */
  override def authenticate(credentials: Credentials): User = {
    User(credentials.username)
  }

  /*
   * Generate the UserToken where the token consists of the username and the current timestamp
   */
  override def issueToken(user: User): UserToken = {
    UserToken(user.userId.concat("_").concat(s"${UserUtil.getCurrentUtcTimestamp}"))
  }

  /*
   * The requestToken() method first gets the userId by invoking the authenticate method of the service
   * once received calls the issueToken() method to generate the token and return the same
   */
  def requestToken(credentials: Credentials): UserToken = {
    val user = TokenServiceImpl.authenticate(credentials)
    issueToken(user)
  }

}
