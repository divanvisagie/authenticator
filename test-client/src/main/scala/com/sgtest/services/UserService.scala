package com.sgtest.services

import javax.inject.Singleton

import com.swissguard.user.thriftscala.{AuthenticationRequest, UserService => TUserService}
import com.twitter.finagle._
import com.twitter.finagle.stats.{DefaultStatsReceiver, NullStatsReceiver}
import com.twitter.finagle.tracing._
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finatra.annotations.Flag
import com.twitter.util.Future


@Singleton
class UserService {

  val receiver = DefaultStatsReceiver.get
  val tracer = ZipkinTracer.mk(
    host = "localhost",
    port = 9410,
    statsReceiver = receiver,
    sampleRate = 1.0f
  )

  private val client: TUserService[Future] = ThriftMux.client
    .withTracer(tracer)
    .withStatsReceiver(receiver)
    .newIface[TUserService.FutureIface]("localhost:9999")

  def registerUser(user: AuthenticationRequest) = client.register(user)

}

//http://localhost:8888/register
//{
//  "username": "joe",
//  "password": "bobby123",
//  "email": "lol"
//}