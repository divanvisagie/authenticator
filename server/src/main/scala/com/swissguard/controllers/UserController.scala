package com.swissguard.controllers

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.swissguard.services.AuthenticationService
import com.swissguard.user.thriftscala.UserService
import com.swissguard.user.thriftscala.UserService.{ListUsers, Login, Register, ValidateToken}
import com.twitter.finatra.thrift.Controller

@Singleton
class UserController @Inject()(authenticationService: AuthenticationService)
  extends Controller
  with UserService.BaseServiceIface {

    override val register = handle(Register) { args: Register.Args =>
      authenticationService.registerUser(
        User.fromAuthenticationRequest(args.user)
      )
    }

    override def login = handle(Login) { args: Login.Args =>
      authenticationService.login(
        User.fromAuthenticationRequest(args.user)
      )
    }

    override def validateToken = handle(ValidateToken) { args: ValidateToken.Args =>
      authenticationService.validate(args.token)
    }

    override def listUsers = handle(ListUsers) { args: ListUsers.Args =>
      authenticationService.listUsers
    }
}
