package com.swissguard

import com.swissguard.user.thriftscala.UserService
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.twitter.util._

class UserListFeatureTest extends FeatureTest with Mockito {
  override val server = new EmbeddedThriftServer(new SwissGuardThriftServer)

  val client = server.thriftClient[UserService[Future]](clientId = "loginClient")

  "user service" should {
    "respond with list > 0" in {
      client.listUsers().value.toList.length should be > 0
    }
  }
}
