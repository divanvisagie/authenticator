package com.swissguard.controllers

import javax.inject.{Inject, Singleton}

import com.swissguard.domain.User
import com.swissguard.user.thriftscala.UserService.{CreateUser, ListUsers, Login, ValidateToken}
import com.swissguard.user.thriftscala.{UserResponse, UserService}
import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future
import com.swissguard.services.{UserService => MyUserService}

@Singleton
class UserController @Inject()(userService: MyUserService)
  extends Controller
  with UserService.BaseServiceIface {

    override val createUser = handle(CreateUser) { args: CreateUser.Args =>
      userService.createUser(
        User.fromUserRequest(args.user)
      )
    }

    override def login = handle(Login) { args: Login.Args =>
      userService.login(
        User.fromUserRequest(args.user)
      )
    }

    override def validateToken = handle(ValidateToken) { args: ValidateToken.Args =>
      Future value (args.token == "token-from-thrift")
    }

    override def listUsers = handle(ListUsers) { args: ListUsers.Args =>
      userService.listUsers
    }
}
