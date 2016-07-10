package com.swissguard.repositories

import javax.inject.Inject

import com.github.ikhoon.TwitterFutureOps._
import com.swissguard.domain.User
import com.twitter.util.{Future => TwitterFuture}
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository @Inject()(db: Database) {

  implicit val session: Session = db.createSession()

  private class UserTable(tag: Tag) extends Table[User](tag, "users"){
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password")
    def email = column[String]("email")
    def * = (id, username, password, email) <> ((User.apply _).tupled, User.unapply)
  }

  private val users = TableQuery[UserTable]

  def findByUsername(username: String): TwitterFuture[Option[User]] =
    db.run {
      users.filter(_.username === username).result.headOption
    }.toTwitterFuture

  def listUsers: TwitterFuture[Seq[User]] =
    db.run {
      users.result
    }.toTwitterFuture

  def createUser(user: User) : TwitterFuture[Option[User]] = {

    val transaction = users.filter(_.username === user.username).result.headOption.flatMap {
      case Some(u) => DBIO.successful(None)
      case None =>
        val userId =
          (users returning users.map(_.id)) += User(
            id = 0,
            email = user.email,
            password = user.password,
            username = user.username
          )

        val newUser = userId.map { id =>
          User(id,user.username,"",user.email)
        }
        newUser
    }.transactionally
    db.run(transaction).map {
      case u: User => Option(u)
      case None => None
    }.toTwitterFuture
  }
}
