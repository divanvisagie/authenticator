package com.swissguard.unittest

import com.swissguard.services.TokenService
import org.scalatest.{FlatSpec, Matchers}

class TokenServiceTest extends FlatSpec with Matchers {
  val tokenService = new TokenService("magnets")

  val payload = Map(
    "username" -> "bob" ,
    "typetest_number" -> 5
  )

  "given claims map" should "receive valid token " in {
     val token = tokenService.generateToken(payload)
     tokenService.validate(token) should be (true)
  }

  "given token with different secret" should "fail validation with different secret" in {
    val otherTokenService = new TokenService("not_magnets")
    val token = otherTokenService.generateToken(payload)
    tokenService.validate(token) should be (false)
  }

  "given token with payload" should "return the same payload" in {
    val token = tokenService.generateToken(payload)
    val extractedPayload = tokenService.getPayloadForToken(token)
    extractedPayload.get should equal (
      Map(
        "username" -> "bob",
        "typetest_number" -> "5"
      )
    )
  }
}
