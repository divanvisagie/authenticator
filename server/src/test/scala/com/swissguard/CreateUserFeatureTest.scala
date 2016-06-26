package com.swissguard

import com.swissguard.user.thriftscala.{AuthenticationRequest, UserService}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.{Await, Future}

class CreateUserFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedThriftServer(new SwissGuardThriftServer)

  val userClient = server.thriftClient[UserService[Future]](clientId = "register")
  "register user bob" should {
    "throw user exists" in {

      the [Exception] thrownBy {
        Await.result(userClient.register(
          AuthenticationRequest("bob","bobby123")
        ))
      } should have message "User exists"

    }
  }
}
