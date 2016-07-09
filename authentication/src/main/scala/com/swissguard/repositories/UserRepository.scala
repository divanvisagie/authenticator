package com.swissguard.repositories

import javax.inject.Inject

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.twitter.finagle.http._
import com.twitter.finagle.Service
import com.swissguard.domain.User
import com.twitter.finagle.exp.mysql.{Client, IntValue, StringValue}
import com.twitter.util.Future

import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository @Inject()(client:  Service[Request,Response]) {
  val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)

  private def createGetRequestWithQuery(query: String) =
    Request(
      Version.Http11,
      Method.Get,
      query
    )


  def findByUsername(username: String): Future[Option[User]] = {
    val request = createGetRequestWithQuery(
      s"/users?username=eq.$username"
    )
    client(request) map { response =>
      val contentJson = response.getContentString()
      val user = mapper.readValue(contentJson, classOf[User])
      Option(user)
    }
  }

  def createUser(user: User) : Future[Option[User]] = {
    Future exception  new Exception("Not Implemented")
  }
}

