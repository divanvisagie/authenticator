package com.swissguard.controllers

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.swissguard.services.{UserService => MyUserService}
import com.swissguard.user.thriftscala.UserService
import com.swissguard.user.thriftscala.UserService.{ListUsers, Login, Register, ValidateToken}
import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future

@Singleton
class UserController @Inject()(userService: MyUserService)
  extends Controller
  with UserService.BaseServiceIface {

    override val register = handle(Register) { args: Register.Args =>
      userService.registerUser(
        User.fromAuthenticationRequest(args.user)
      )
    }

    override def login = handle(Login) { args: Login.Args =>
      userService.login(
        User.fromAuthenticationRequest(args.user)
      )
    }

    override def validateToken = handle(ValidateToken) { args: ValidateToken.Args =>
      Future.value(args.token == "token-from-thrift")
    }

    override def listUsers = handle(ListUsers) { args: ListUsers.Args =>
      userService.listUsers
    }
}
