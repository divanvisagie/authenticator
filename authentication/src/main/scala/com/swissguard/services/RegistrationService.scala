package com.swissguard.services

import javax.inject.Inject
import com.swissguard.domain.User
import com.swissguard.repositories.UserRepository
import com.twitter.util.Future
import com.github.t3hnar.bcrypt._

class RegistrationService @Inject()(userRepository: UserRepository) {

  def registerUser(user: User): Future[Boolean] = {
    val safeUser = User(
      id = user.id ,
      password = user.password.bcrypt,
      username = user.username,
      email = ""
    )
    userRepository.createUser(safeUser) flatMap {
      case None => Future.exception(new Exception("User exists"))
      case Some(user: User) => Future.value(true)
    }
  }
}
