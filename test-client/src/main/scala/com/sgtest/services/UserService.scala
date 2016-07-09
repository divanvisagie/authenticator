package com.sgtest.services

import javax.inject.Singleton
import com.swissguard.authentication.thriftscala.{RegistrationRequest, LoginRequest,  AuthenticationService => TAuthenticationService}
import com.twitter.finagle._
import com.twitter.finagle.service.RetryBudget
import com.twitter.finagle.stats.{DefaultStatsReceiver, NullStatsReceiver}
import com.twitter.finagle.tracing._
import com.twitter.finagle.zipkin.thrift.ZipkinTracer
import com.twitter.finatra.annotations.Flag
import com.twitter.util.Future
import com.twitter.conversions.time._

@Singleton
class UserService {

  val receiver = DefaultStatsReceiver.get
  val tracer = ZipkinTracer.mk(
    host = "localhost",
    port = 9410,
    statsReceiver = receiver,
    sampleRate = 1.0f
  )

  val budget: RetryBudget = RetryBudget(
    ttl = 10.seconds,
    minRetriesPerSec = 5,
    percentCanRetry = 0.1
  )

  private val client: TAuthenticationService[Future] = ThriftMux.client
    .withTracer(tracer)
    .withStatsReceiver(receiver)
    .withLabel("test-client")
    .newIface[TAuthenticationService.FutureIface]("localhost:9999")

  def registerUser(registrationRequest: RegistrationRequest) = client.register(registrationRequest)
  def login(loginRequest: LoginRequest) = client.login(loginRequest)
}

//http://localhost:8888/register
//{
//  "username": "joe",
//  "password": "bobby123",
//  "email": "lol"
//}