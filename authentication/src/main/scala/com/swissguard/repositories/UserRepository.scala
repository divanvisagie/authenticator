package com.swissguard.repositories

import javax.inject.Inject

import com.swissguard.domain.User
import com.twitter.finagle.exp.mysql.{Client, IntValue, StringValue}
import com.twitter.util.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository @Inject()(dbClient: Client) {


  def findByUsername(username: String): Future[Option[User]] = {
    val statement = "SELECT * FROM user WHERE username = ?"
    val prepared = dbClient.prepare(statement).toString
    dbClient.select(prepared) { row =>
      val IntValue(id) = row("id").getOrElse(0)
      val StringValue(username) = row("username").get
      val StringValue(password) = row("password_hash").get
      val StringValue(email) = row("email").get
      User(
        id = id,
        username = username,
        password = password,
        email = email
      )
    }.map(_.headOption)
  }

  def createUser(user: User) : Future[Option[User]] = {
    Future exception  new Exception("Not Implemented")
  }
}

