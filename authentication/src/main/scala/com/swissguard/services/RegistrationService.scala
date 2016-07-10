package com.swissguard.services

import javax.inject.Inject

import com.github.t3hnar.bcrypt._
import com.google.inject.Singleton
import com.swissguard.domain.User
import com.swissguard.repositories.UserRepository
import com.twitter.util.Future

@Singleton
class RegistrationService @Inject()(userRepository: UserRepository,emailService: EmailService) {

  def registerUser(user: User): Future[Boolean] = {
    val safeUser = User(
      id = user.id,
      password = user.password.bcrypt,
      username = user.username,
      email = ""
    )
    userRepository.createUser(safeUser) flatMap {
      case None => Future.exception(new Exception("User exists"))
      case Some(user: User) =>
        emailService.sendSignupEmailToUser(user)
        Future.value(true)
    }
  }
}
