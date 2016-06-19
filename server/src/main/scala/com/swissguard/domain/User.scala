package com.swissguard.domain

import com.swissguard.user.thriftscala.UserRequest

case class User(
  id: Int,
  username: String,
  password: String
)
object User {
  def fromUserRequest(userRequest: UserRequest) : User =
    User(
      id = 0,
      username = userRequest.username,
      password = userRequest.password
    )
}
