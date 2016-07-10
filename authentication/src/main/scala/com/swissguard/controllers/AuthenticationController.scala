package com.swissguard.controllers

import javax.inject.{Inject, Singleton}

import com.swissguard.authentication.thriftscala.{AuthenticationService => TAuthenticationService}
import com.swissguard.domain.User
import com.swissguard.services.{AuthenticationService, LoginService, RegistrationService}
import com.swissguard.authentication.thriftscala.AuthenticationService.{ClaimsForToken, Login, Register, ValidateToken, InvalidateToken}
import com.twitter.finatra.thrift.Controller
import com.twitter.util.Future

@Singleton
class AuthenticationController @Inject()(
    authenticationService: AuthenticationService,
    registrationService: RegistrationService,
    loginService: LoginService
  )
  extends Controller
  with TAuthenticationService.BaseServiceIface {

    override val register = handle(Register) { args: Register.Args =>
      registrationService.registerUser(
        User.fromRegistrationRequest(args.registrationRequest)
      )
    }

    override def login = handle(Login) { args: Login.Args =>
      loginService.login(
        User.fromLoginRequest(args.loginRequest)
      )
    }

    override def validateToken = handle(ValidateToken) { args: ValidateToken.Args =>
      authenticationService.validate(args.token)
    }

    override def claimsForToken = handle(ClaimsForToken) { args: ClaimsForToken.Args =>
      authenticationService.claimsForToken(args.token)
    }

    override  def invalidateToken = handle(InvalidateToken) { args: InvalidateToken.Args =>
      Future exception new Exception("Not Implemented")
    }
}
