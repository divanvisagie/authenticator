package com.sgtest

import java.net.InetSocketAddress

import com.sgtest.controllers.AuthenticationController
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter


object TestClientServerMain extends TestClientServer

class TestClientServer extends HttpServer {

  override def defaultFinatraHttpPort = ":8888"

  override val adminPort = flag("admin.port", new InetSocketAddress(8880), "Admin Htto server port")

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .add[AuthenticationController]
  }

}