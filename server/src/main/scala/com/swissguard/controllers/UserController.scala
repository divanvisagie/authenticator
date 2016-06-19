package com.swissguard.controllers

import javax.inject.Singleton

import com.swissguard.user.thriftscala.UserService.{CreateUser, Login, ValidateToken}
import com.swissguard.user.thriftscala.{UserResponse, UserService}
import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future

@Singleton
class UserController
  extends Controller
  with UserService.BaseServiceIface {

    val responseUser = UserResponse(
      id = 1,
      username = "bob",
      token = "token-from-thrift"
    )
    override val createUser = handle(CreateUser) { args: CreateUser.Args =>
      Future value responseUser
    }

    override def login = handle(Login) { args: Login.Args =>
      args.user.password match {
        case password if password == "bobby123" => Future value responseUser
        case _ => Future exception new Exception ("Invalid password")
      }
    }

    override def validateToken = handle(ValidateToken) { args: ValidateToken.Args =>
      Future value (args.token == "token-from-thrift")
    }
}
