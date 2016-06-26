package com.swissguard.domain

import com.swissguard.user.thriftscala.{AuthenticationRequest, UserRequest, UserResponse}

case class User(
  id: Int,
  username: String,
  password: String
) {
}
object User {
  def fromAuthenticationRequest(authenticationRequest: AuthenticationRequest): User =
    User(
      id = 0,
      username = authenticationRequest.username,
      password = authenticationRequest.password
    )

  def fromUserRequest(userRequest: UserRequest) : User =
    User(
      id = 0,
      username = userRequest.username,
      password = userRequest.password
    )

  def toUserResponse(user: User, token: String = "") : UserResponse =
    UserResponse(
      username = user.username,
      token = token,
      id = user.id
    )


}
