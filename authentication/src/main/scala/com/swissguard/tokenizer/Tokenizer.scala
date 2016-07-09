package com.swissguard.tokenizer

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}

class Tokenizer(secret: String) {

  def generateToken(payload: Map[String, Any]): String = {
    val header = JwtHeader("HS256")
    val claimSet = JwtClaimsSet(
      payload
    )
    JsonWebToken(header, claimSet, secret)
  }

  def validate(token: String): Boolean =
    JsonWebToken.validate(token, secret)

  def getPayloadForToken(token: String): Option[Map[String, String]] = token match {
    case JsonWebToken(header, claimsSet, signature) =>
      claimsSet.asSimpleMap.toOption
    case x =>
      None
  }

}

