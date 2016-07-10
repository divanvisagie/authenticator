package com.swissguard.services

import javax.inject.{Inject, Singleton}

import com.twitter.util.Future
import com.swissguard.repositories.UserRepository
import com.swissguard.authentication.thriftscala.Claims
import com.swissguard.tokenizer.Tokenizer

@Singleton
class AuthenticationService @Inject()(
 userRepository: UserRepository,
 tokenizer: Tokenizer
) {

  def validate(token: String): Future[Boolean] = {
    Future value tokenizer.validate(token)
  }

  def claimsForToken(token: String): Future[Claims] = {

    tokenizer.getPayloadForToken(token).getOrElse(None) match {
      case Some(payload: Map[String,String]) => Future value Claims (
        userId = payload("userId"),
        username = payload("userName"),
        claims = List()
      )
      case _ => Future exception new Exception("No payload")
    }

  }

}
