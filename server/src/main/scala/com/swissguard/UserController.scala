package com.swissguard

import com.swissguard.user.thriftscala.UserService
import com.swissguard.user.thriftscala.UserService.CreateUser


import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future
import javax.inject.Singleton

@Singleton
class UserController
  extends Controller
  with UserService.BaseServiceIface {

    override val createUser = handle(CreateUser) { args: CreateUser.Args =>
      Future.value("token-from-thrift")
    }
}
