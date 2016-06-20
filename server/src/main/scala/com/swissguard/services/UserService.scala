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
  val responseUser = UserResponse(
    id = 1,
    username = "bob",
    token = "token-from-thrift"
  )

  def createUser(user: User): Future[UserResponse] = {
    val safeUser = User(
      id = user.id ,
      password = user.password.bcrypt,
      username = user.username
    )
    userRepository.createUser(safeUser).toTwitterFuture map {
      case Some(u: User) => User.toUserResponse(safeUser, "token-from-thrift")
      case _ => throw new Exception("User exists")
    }
  }

  def login(user: User): Future[Boolean] =
    userRepository.findByUsername(user.username).toTwitterFuture map[Boolean] {
      case Some(u: User) => user.password.isBcrypted(u.password.toString)
      case _ => false
    }

  def findUserByUsername(username: String): Future[Option[User]] =
    userRepository.findByUsername(username).toTwitterFuture


  def listUsers : Future[Seq[UserResponse]] =
    userRepository.listUsers.toTwitterFuture map { userList =>
      userList.map { user =>
        User.toUserResponse(user)
      }
    }

}
