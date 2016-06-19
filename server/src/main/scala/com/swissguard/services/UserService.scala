package com.swissguard.services

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.twitter.util.Future
import com.swissguard.repositories.UserRepository
import com.github.ikhoon.TwitterFutureOps._
import com.swissguard.user.thriftscala.UserResponse


@Singleton
class UserService @Inject()(userRepository: UserRepository) {
  val responseUser = UserResponse(
    id = 1,
    username = "bob",
    token = "token-from-thrift"
  )

  def createUser(user: User): Future[UserResponse] =
    Future value responseUser

  def login(user: User): Future[UserResponse] =
    user.password match {
      case password if password == "bobby123" => Future value responseUser
      case _ => Future exception new Exception ("Invalid password")
    }

  def findUserByUsername(username: String): Future[Option[User]] =
    userRepository.findByUsername(username).toTwitterFuture


  def listUsers : Future[List[User]] =
    userRepository.listUsers.toTwitterFuture

}
