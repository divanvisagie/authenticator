package com.example

import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future
import com.swissguard.ExampleServer
import com.swissguard.user.thriftscala.{User, UserService}

class ExampleServerFeatureTest extends FeatureTest {

  override val server = new EmbeddedThriftServer(new ExampleServer)

  val userClient = server.thriftClient[UserService[Future]](clientId = "client123")
  "user service" should {
    "respond with token" in {
      userClient.createUser(User("bob")).value should be ("token-from-thrift")
    }
  }
}
