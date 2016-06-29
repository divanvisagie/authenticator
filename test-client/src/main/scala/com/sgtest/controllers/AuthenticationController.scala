package com.sgtest.controllers

import javax.inject.Inject
import com.sgtest.domain.http.RegisterUserRequest
import com.sgtest.services.UserService
import com.twitter.finatra.http.Controller



class AuthenticationController @Inject()(userService: UserService)
  extends Controller {

  post("/register") { request: RegisterUserRequest =>
    userService.registerUser(request.toThrift) handle {
      case _: Exception => response.status(420)
    }
  }

}
