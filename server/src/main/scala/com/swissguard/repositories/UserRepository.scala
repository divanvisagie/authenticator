package com.swissguard.repositories

import javax.inject.Inject
import com.swissguard.domain.User
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository @Inject()(db: Database) {

  implicit val session: Session = db.createSession()

  private class UserTable(tag: Tag) extends Table[User](tag, "users"){
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def password = column[String]("password_hash")
    def * = (id, username, password) <> ((User.apply _).tupled, User.unapply)
  }

  private val users = TableQuery[UserTable]

  def findByUsername(username: String): Future[Option[User]] =
    db.run {
      users.filter(_.username === username).take(1).result
    }.map(_.headOption)

  def listUsers: Future[Seq[User]] =
    db.run {
      users.result
    }

  def createUser(user: User) : Future[User] = {
    db.run {
      users.map(p => (p.username, p.password)) += (user.username, user.password)
    }

    db.run {
      users.filter(_.username === user.username).take(1).result
    }.map(_.head)
  }

}

