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
    userRepository.createUser(safeUser).toTwitterFuture map { returnUser =>
      User.toUserResponse(returnUser, "token-from-thrift")
    }
  }

  def login(user: User): Future[UserResponse] = {

    user.password match {
      case password if password == "bobby123" => Future value responseUser
      case _ => Future exception new Exception("Invalid password")
    } //"password".isBcrypted("$2a$10$iXIfki6AefgcUsPqR.niQ.FvIK8vdcfup09YmUxmzS/sQeuI3QOFG")
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
