package com.swissguard

import com.google.inject.testing.fieldbinder.Bind
import com.swissguard.user.thriftscala.{UserRequest, UserService}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util._

class LoginFeatureTest extends FeatureTest {

  override val server = new EmbeddedThriftServer(new ExampleServer)

  val client = server.thriftClient[UserService[Future]](clientId = "loginClient")

  "user service" should {
    "respond to login with token" in {
      client.login(
        UserRequest("bob","bobby123")
      ).value.token should be ("token-from-thrift")
    }
  }

  "user service" should {
    "respond with nothing" in {
      client.login(
        UserRequest("bob", "sarah123")
      ).onSuccess{ _ =>
        "" should be ("should have failed")
      }.onFailure( err => {
        err.getMessage should be ("Invalid password")
      })
    }
  }
}

class UserListFeatureTest extends FeatureTest with Mockito {
  override val server = new EmbeddedThriftServer(new ExampleServer)

  val client = server.thriftClient[UserService[Future]](clientId = "loginClient")

  "user service" should {
    "respond with list > 0" in {
      client.listUsers().value.toList.length should be > 0
    }
  }
}


