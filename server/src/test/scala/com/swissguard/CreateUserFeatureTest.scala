package com.swissguard

import com.swissguard.user.thriftscala.{UserRequest, UserService}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class CreateUserFeatureTest extends FeatureTest {

  override val server = new EmbeddedThriftServer(new ExampleServer)

  val userClient = server.thriftClient[UserService[Future]](clientId = "createClient")
  "user service" should {
    "respond to createUser with token" in {
      userClient.createUser(
        UserRequest("bob","bobby123")
      ).value.token should be ("token-from-thrift")
    }
  }
}
