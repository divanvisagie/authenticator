package com.swissguard.unittest

import com.swissguard.services.TokenService
import org.scalatest.{FlatSpec, Matchers}

class TokenServiceTest extends FlatSpec with Matchers {
  val tokenService = new TokenService("magnets")

  val claims = Map(
    "username" -> "bob" ,
    "typetest_number" -> 5
  )

  "given claims map" should "receive valid token " in {
     val token = tokenService.generateToken(claims)
     tokenService.validate(token) should be (true)
  }

  "given token with different secret" should "fail validation with different secret" in {
    val otherTokenService = new TokenService("not_magnets")
    val token = otherTokenService.generateToken(claims)
    tokenService.validate(token) should be (false)
  }
}
