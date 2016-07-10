package com.swissguard.domain

import com.swissguard.authentication.thriftscala.{LoginRequest, RegistrationRequest}

case class User(
  id: Int,
  username: String,
  password: String,
  email: String
) {
}
object User {
  def fromLoginRequest(loginRequest: LoginRequest) = {
    User(
      id = 0,
      username = loginRequest.username,
      password = loginRequest.password,
      email = ""
    )
  }

  def fromRegistrationRequest(registrationRequest: RegistrationRequest) = {
    User(
      id = 0,
      username = registrationRequest.username,
      password = registrationRequest.password,
      email = registrationRequest.email
    )
  }
}
