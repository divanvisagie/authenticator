package com.swissguard.services

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.swissguard.repositories.UserRepository
import com.twitter.util.Future
import com.github.t3hnar.bcrypt._
import com.swissguard.tokenizer.Tokenizer

@Singleton
class LoginService @Inject()(userRepository: UserRepository, tokenizer: Tokenizer) {

  private def authenticateUser(user: User,password: String): Future[String] = {
    if (password.isBcrypted(user.password.toString)) generateTokenForUser(user)
    else Future.exception(new Exception("Invalid password"))
  }


  private def generateTokenForUser(user: User): Future[String] = {
    Future value tokenizer.generateToken(
      Map(
        "username" -> user.username
      )
    )
  }

  private def findUserByUsername(username: String): Future[User] =
    userRepository.findByUsername(username) flatMap {
      case None => Future.exception(new Exception("User not found"))
      case Some(user) => Future.value(user)
    }

  def login(user: User): Future[String] =
    for {
      u <- findUserByUsername(user.username)
      token <- authenticateUser(u, user.password)
    } yield {
      token
    }
}
