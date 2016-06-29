package com.sgtest.domain.http

import com.swissguard.user.thriftscala.AuthenticationRequest
import com.twitter.finatra.validation._

case class RegisterUserRequest(
  @Size(min = 2, max = 100) username: String,
  email: String,
  password: String
) {

  def toThrift = {
    AuthenticationRequest(
      username = username,
      password = password
    )
  }
}