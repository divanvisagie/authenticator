package com.example

import com.google.inject.Stage
import com.twitter.finatra.thrift.EmbeddedThriftServer
import com.twitter.inject.server.FeatureTest
import com.swissguard.SwissGuardThriftServer

class ExampleServerStartupTest extends FeatureTest {

  val server = new EmbeddedThriftServer(
    twitterServer = new SwissGuardThriftServer,
    stage = Stage.PRODUCTION)

  "server" should {
    "startup" in {
      server.assertHealthy()
    }
  }
}