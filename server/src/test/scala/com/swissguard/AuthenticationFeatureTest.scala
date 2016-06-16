package com.swissguard

import com.swissguard.user.thriftscala.UserService
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class AuthenticationFeatureTest extends FeatureTest {

  override val server = new EmbeddedThriftServer(new ExampleServer)

  val client = server.thriftClient[UserService[Future]](clientId = "loginClient")

  "user service" should {
    "respond to validation with true" in {
      client.validateToken("token-from-thrift")
        .value should be (true)
    }
  }

  "user service" should {
    "respond to validation with false" in {
      client.validateToken("bad-token")
        .value should be (false)
    }
  }
}
