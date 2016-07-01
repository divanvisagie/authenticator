package com.swissguard.services

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}
import com.swissguard.domain.User

class TokenService {

  val secret = "magnets"

  def generateToken(user: User): String = {
    val header =  JwtHeader("HS256")
    val claimSet = JwtClaimsSet(
      User.toMap(user)
    )
    JsonWebToken(header, claimSet, secret)
  }

  def validate(token: String): Boolean =
    JsonWebToken.validate(token , secret)

  def getClaimsForToken(token: String): Option[Map[String, String]] = token match {
    case JsonWebToken(header, claimsSet, signature) =>
      claimsSet.asSimpleMap.toOption
    case x =>
      None
  }

  def userForToken(token: String): Option[User] = {
    val claims = getClaimsForToken(token).getOrElse {
       return None
    }
    Option(User.fromMap(claims))
  }
}
