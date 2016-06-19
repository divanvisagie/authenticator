package com.swissguard.repositories

import javax.inject.Inject
import com.swissguard.domain.User
import slick.driver.PostgresDriver.api._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository @Inject()(db: Database) {

  implicit val session: Session = db.createSession()

  private class UserTable(tag: Tag) extends Table[User](tag, "users"){
    def id = column[Int]("id")
    def username = column[String]("username")
    def * = (id, username) <> ((User.apply _).tupled, User.unapply)
  }

  private val users = TableQuery[UserTable]

  def findByUsername(username: String): Future[Option[User]] =
    db.run {
      users.filter(_.username === username).take(1).result
    }.map(_.headOption)

  def listUsers: Future[List[User]] =
    db.run {
      users.result
    }.map(_.toList)

}

