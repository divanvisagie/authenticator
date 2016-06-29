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
      users.filter(_.username === username).result.headOption
    }

  def listUsers: Future[Seq[User]] =
    db.run {
      users.result
    }

  def createUser(user: User) : Future[Option[User]] = {

    val transaction = users.filter(_.username === user.username).result.headOption.flatMap {
      case Some(u) => DBIO.successful(None)
      case None =>
        val userId =
        (users returning users.map(_.id)) += User(
          id = 0,
          password = user.password,
          username = user.username
        )

        val newUser = userId.map { id =>
          User(id,user.username,"")
        }
        newUser
    }.transactionally
    db.run(transaction).map {
      case u: User => Option(u)
      case None => None
    }

  }

}

