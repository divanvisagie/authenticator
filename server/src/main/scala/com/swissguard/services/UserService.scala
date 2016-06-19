package com.swissguard.services

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.twitter.util.Future

import com.swissguard.repositories.UserRepository
import com.github.ikhoon.TwitterFutureOps._


@Singleton
class UserService @Inject()(userRepository: UserRepository) {

  def findUserByUsername(username: String): Future[Option[User]] =
    userRepository.findByUsername(username).toTwitterFuture


  def listUsers : Future[List[User]] =
    userRepository.listUsers.toTwitterFuture

}
