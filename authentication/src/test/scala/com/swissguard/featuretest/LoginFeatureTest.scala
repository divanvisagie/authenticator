package com.swissguard.featuretest

import com.swissguard.SwissGuardThriftServer
import com.swissguard.authentication.thriftscala.{AuthenticationService, LoginRequest}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.{Await, Future}

class LoginFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedThriftServer(new SwissGuardThriftServer)

  val client = server.thriftClient[AuthenticationService[Future]](clientId = "login")


  "login with correct password" should {
    "respond with token" in {
      client.login(
        LoginRequest("bob","bobby123")
      ).value.length should be > 20
    }
  }

  "login with incorrect password" should {
    "throw Invalid password exception" in {

      val thrown = the [Exception] thrownBy {
        Await.result(client.login(
          LoginRequest("bob", "sarah123")
        ))
      }
      thrown.toString should include ("Invalid password")
    }
  }

  "login with incorrect username" should {
    "throw invalid Username exception" in {
      val thrown = the [Exception] thrownBy {
        Await.result(client.login(
          LoginRequest("alan-nowhere", "wont-matter")
        ))
      }
      thrown.toString should include ("User not found")
    }
  }
}




