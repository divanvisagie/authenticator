package com.swissguard

import com.swissguard.controllers.UserController
import com.swissguard.modules.DatabaseModule
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.finatra.thrift.filters._

object SwissGuardServerMain extends SwissGuardThriftServer

class SwissGuardThriftServer extends ThriftServer {
  override val name = "swiss-guard"

  override def modules = Seq(DatabaseModule)

  override def configureThrift(router: ThriftRouter) {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[AccessLoggingFilter]
      .filter[StatsFilter]
      .filter[ExceptionTranslationFilter]
      .add[UserController] //We can only have one in thrift
  }

  override def configureThriftServer(server: ThriftMux.Server): ThriftMux.Server = {
    val receiver = DefaultStatsReceiver.get
    val tracer = ZipkinTracer.mk(
      host = "localhost",
      port = 9410,
      statsReceiver = receiver,
      sampleRate = 1.0f
    )

    server
       .withTracer(tracer)
       .withLabel("swiss-guard-server")

  }
}
