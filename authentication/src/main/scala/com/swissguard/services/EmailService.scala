package com.swissguard.services

import javax.inject.Singleton

import akka.actor.ActorSystem
import com.swissguard.actors.EmailSystemActor
import com.swissguard.domain.User

@Singleton
class EmailService {
  val system = ActorSystem("email-system")
  val systemActor = system.actorOf(EmailSystemActor.props,"emailSystemActor")

  def sendSignupEmailToUser(user: User): Unit = {
    systemActor ! user
  }
}
