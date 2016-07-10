package com.swissguard.domain


case class EmailMessage(username: String, email: String, message: String)

object EmailMessage {
  def fromUser(user: User) {
    EmailMessage(
      username = user.username,
      email = user.email,
      message = s"You registered ${user.username} at this email"
    )
  }
}
