package com.swissguard

import scala.slick.driver.PostgresDriver.simple._

// CREATE TABLE users
// (
//   id bigserial NOT NULL,
//   username character varying,
//   CONSTRAINT pk PRIMARY KEY (id)
// )
// insert into users(username) values ('john'), ('mary');
object SlickExample {

  // this is a class that represents the table I've created in the database
  class Users(tag: Tag) extends Table[(Int, String)](tag, "users") {
    def id = column[Int]("id")
    def username = column[String]("username")
    def * = (id, username)
  }

  def doQuery(args: Array[String]): Unit = {

    // my database server is located on the localhost
    // database name is "my-db"
    // username is "postgres"
    // and password is "postgres"
    val connectionUrl = "jdbc:postgresql://localhost:1337/swissguard?user=postgres&password=postgres"

    Database.forURL(connectionUrl, driver = "org.postgresql.Driver") withSession {
      implicit session =>
        val users = TableQuery[Users]

        // SELECT * FROM users
        users.list foreach { row =>
          println("user with id " + row._1 + " has username " + row._2)
        }

        // SELECT * FROM users WHERE username='john'
        users.filter(_.username === "john").list foreach { row =>
           println("user whose username is 'john' has id "+row._1 )
        }
    }
  }
}