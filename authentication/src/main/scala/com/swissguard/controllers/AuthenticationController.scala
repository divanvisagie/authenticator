package com.swissguard.controllers

import javax.inject.{Inject, Singleton}

import com.swissguard.authentication.thriftscala.{AuthenticationService => TAuthenticationService}
import com.swissguard.domain.User
import com.swissguard.services.AuthenticationService
import com.swissguard.authentication.thriftscala.AuthenticationService.{ClaimsForToken, Login, Register, ValidateToken}
import com.twitter.finatra.thrift.Controller

@Singleton
class AuthenticationController @Inject()(authenticationService: AuthenticationService)
  extends Controller
  with TAuthenticationService.BaseServiceIface {

    override val register = handle(Register) { args: Register.Args =>
      authenticationService.registerUser(
        User.fromRegistrationRequest(args.registrationRequest)
      )
    }

    override def login = handle(Login) { args: Login.Args =>
      authenticationService.login(
        User.fromLoginRequest(args.loginRequest)
      )
    }

    override def validateToken = handle(ValidateToken) { args: ValidateToken.Args =>
      authenticationService.validate(args.token)
    }

    override def claimsForToken = handle(ClaimsForToken) { args: ClaimsForToken.Args =>
      authenticationService.claimsForToken(args.token)
    }
}
