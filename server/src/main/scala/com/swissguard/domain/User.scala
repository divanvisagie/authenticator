package com.swissguard.domain

import com.swissguard.user.thriftscala.{AuthenticationRequest, UserResponse}

case class User(
  id: Int,
  username: String,
  password: String
) {
}
object User {
  def fromMap(userMap: Map[String, Any]): User =
    User(
      id = 0,
      username = userMap.get("username").toString,
      password = ""
    )

  def toMap(user: User): Map[String, Any] = {
    Map(
      "username" -> user.username
    )
  }

  def fromAuthenticationRequest(authenticationRequest: AuthenticationRequest): User =
    User(
      id = 0,
      username = authenticationRequest.username,
      password = authenticationRequest.password
    )

  def fromUserRequest(authenticationRequest: AuthenticationRequest) : User =
    User(
      id = 0,
      username = authenticationRequest.username,
      password = authenticationRequest.password
    )

  def toUserResponse(user: User, token: String = "") : UserResponse =
    UserResponse(
      username = user.username,
      token = token,
      id = user.id
    )


}
