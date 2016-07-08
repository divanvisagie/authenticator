package com.swissguard.featuretest

import com.swissguard.SwissGuardThriftServer
import com.swissguard.authentication.thriftscala.{AuthenticationService, RegistrationRequest}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util.{Await, Future}

class CreateUserFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedThriftServer(new SwissGuardThriftServer)

  val client = server.thriftClient[AuthenticationService[Future]](clientId = "register")

  "register user bob " should {
    "throw user exists" in {

      val thrown = the[Exception] thrownBy {
        Await.result(client.register(
          RegistrationRequest(
            username = "bob",
            password = "bob",
            email = "bob@me.com"
          )
        ))
      }
      thrown.toString should include("User exists")
    }
  }
}
