package com.swissguard.featuretest

import com.swissguard.SwissGuardThriftServer
import com.swissguard.authentication.thriftscala.AuthenticationService
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.server.FeatureTest
import com.twitter.util.Future

class AuthenticationFeatureTest extends FeatureTest {

  override val server = new EmbeddedThriftServer(new SwissGuardThriftServer)

  val client = server.thriftClient[AuthenticationService[Future]](clientId = "loginClient")

  "user service" should {
    "respond to validation with true" in {
      client.validateToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6ImJvYmEifQ.fQ2PO8HbCgGzmVoyM6RBrzXjYseUgv1VgpwWx9FCBxY")
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
