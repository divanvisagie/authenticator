package com.swissguard

import com.google.inject.testing.fieldbinder.Bind
import com.swissguard.domain.User
import com.swissguard.services.UserService
import com.twitter.inject.Mockito
import com.swissguard.user.thriftscala.{UserRequest, UserService => ThriftUserService}
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class CreateUserFeatureTest extends FeatureTest with Mockito {

  override val server = new EmbeddedThriftServer(new ExampleServer)


  val userClient = server.thriftClient[ThriftUserService[Future]](clientId = "createClient")
  "user service" should {
    "respond to createUser with token" in {
      userClient.createUser(
        UserRequest("bob","bobby123")
      ).value.token should be ("token-from-thrift")
    }
  }
}
