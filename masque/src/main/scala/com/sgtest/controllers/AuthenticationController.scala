package com.sgtest.controllers

import javax.inject.Inject
import com.sgtest.services.UserService
import com.twitter.finatra.http.Controller
import com.swissguard.authentication.thriftscala.{RegistrationRequest, LoginRequest}

class AuthenticationController @Inject()(userService: UserService)
  extends Controller {

  post("/register") { request: RegistrationRequest =>
    userService.registerUser(request) handle {
      case _: Exception => response.status(420)
    }
  }

  post("/login") { request: LoginRequest =>
    userService.login(request) handle {
      case e: Exception => response.status(401)
        response.unauthorized( e.getMessage)
    }
  }

}
