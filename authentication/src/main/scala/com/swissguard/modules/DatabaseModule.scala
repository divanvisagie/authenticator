package com.swissguard.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.finagle.Http
import com.twitter.finagle.Service
import com.twitter.finagle.Http.Client
import com.twitter.finagle.http.Request
import com.twitter.inject.TwitterModule
import com.twitter.finagle.http._
import com.twitter.finagle.stats.DefaultStatsReceiver
import com.twitter.finagle.zipkin.thrift.ZipkinTracer

object DatabaseModule extends TwitterModule {

  val server = sys.env.getOrElse("POSTGREST_HOST","localhost:3000")

  val receiver = DefaultStatsReceiver.get
  val zipkinHost = sys.env.getOrElse("SG_ZIPKIN_HOST","localhost")
  val zipkinPort = sys.env.getOrElse("SG_ZIPKIN_PORT", "9410").toInt

  val tracer = ZipkinTracer.mk(
    host = zipkinHost,
    port = zipkinPort,
    statsReceiver = receiver
  )

  @Singleton @Provides
  def provideFinagleClient(): Service[Request, Response] = {
    Http.client
      .withTracer(tracer)
      .withLabel("sg-postgrest-client")
      .newService(server)
  }
}
