package com.swissguard.services

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.twitter.util.Future
import com.swissguard.repositories.UserRepository
import com.github.ikhoon.TwitterFutureOps._
import com.github.t3hnar.bcrypt._
import com.swissguard.user.thriftscala.UserResponse


@Singleton
class UserService @Inject()(userRepository: UserRepository) {

  private def createUser(user: User): Future[User] = {
    val safeUser = User(
      id = user.id ,
      password = user.password.bcrypt,
      username = user.username
    )
    userRepository.createUser(safeUser).toTwitterFuture flatMap {
      case None => Future.exception(new Exception("User exists"))
      case Some(user: User) => Future.value(user)
    }
  }

  private def generateTokenForUser(user: User): Future[String] =
    Future value "token-from-thrift"

  private def authenticateUser(user: User,password: String): Future[String] = {
    if (password.isBcrypted(user.password.toString)) generateTokenForUser(user)
    else Future.exception(new Exception("Invalid password"))
  }

  def registerUser(user: User): Future[String] =
    for {
      u <- createUser(user)
      token <- generateTokenForUser(u)
    } yield {
      token
    }


  def login(user: User): Future[String] =
    for {
      u <- findUserByUsername(user.username)
      token <- authenticateUser(u, user.password)
    } yield {
      token
    }


  private def findUserByUsername(username: String): Future[User] =
    userRepository.findByUsername(username).toTwitterFuture flatMap {
      case None => Future.exception(new Exception("User not found"))
      case Some(user) => Future.value(user)
    }


  def listUsers : Future[Seq[UserResponse]] =
    userRepository.listUsers.toTwitterFuture map { userList =>
      userList.map { user =>
        User.toUserResponse(user)
      }
    }

}
