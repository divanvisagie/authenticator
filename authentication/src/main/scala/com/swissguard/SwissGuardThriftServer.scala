package com.swissguard

import com.swissguard.controllers.UserController
import com.swissguard.modules.{DatabaseModule, TokenServiceModule}
import com.twitter.finagle.ThriftMux
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.finatra.thrift.filters._

object SwissGuardServerMain extends SwissGuardThriftServer

class SwissGuardThriftServer extends ThriftServer {
  override val name = "swiss-guard"

  override def modules = Seq(DatabaseModule,TokenServiceModule)

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
    val zipkinHost = sys.env.getOrElse("SG_ZIPKIN_HOST","localhost")
    val zipkinPort = sys.env.getOrElse("SG_ZIPKIN_PORT", "9410").toInt
    val tracer = ZipkinTracer.mk(
      host = zipkinHost,
      port = zipkinPort,
      statsReceiver = receiver
    )

    server
       .withTracer(tracer)
       .withLabel("swiss-guard-server")

  }
}
