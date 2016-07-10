package com.swissguard.actors

import akka.actor.{Actor, Props}
import akka.event.Logging
import com.swissguard.domain.{EmailMessage, User}

class EmailSystemActor extends Actor {
  val log = Logging.getLogger(context.system, this)

  def receive = {
    case user: User => {
      val mailMessage = EmailMessage.fromUser(user)
      log.info(mailMessage.toString)
    }
  }
}
object EmailSystemActor {
  val props = Props[EmailSystemActor]
}
