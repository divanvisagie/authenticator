package com.swissguard

import com.swissguard.user.thriftscala.{AuthenticationRequest, UserService}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.{Await, Future}

class LoginFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedThriftServer(new SwissGuardThriftServer)

  val client = server.thriftClient[UserService[Future]](clientId = "login")

  "login with correct password" should {
    "respond with token" in {
      client.login(
        AuthenticationRequest("bob","bobby123")
      ).value should be ("token-from-thrift")
    }
  }

  "login with incorrect password" should {
    "throw Invalid password exception" in {

      val thrown = the [Exception] thrownBy {
        Await.result(client.login(
          AuthenticationRequest("bob", "sarah123")
        ))
      }
      thrown.toString should include ("Invalid password")
    }
  }

  "login with incorrect username" should {
    "throw invalid Username exception" in {
      val thrown = the [Exception] thrownBy {
        Await.result(client.login(
          AuthenticationRequest("alan-nowhere", "wont-matter")
        ))
      }
      thrown.toString should include ("User not found")
    }
  }
}




