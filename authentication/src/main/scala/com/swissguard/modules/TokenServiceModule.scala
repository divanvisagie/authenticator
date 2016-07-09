package com.swissguard.modules

import com.google.inject.{Provides, Singleton}
import com.swissguard.tokenizer.Tokenizer
import com.twitter.inject.TwitterModule

object TokenServiceModule extends TwitterModule {

  val secret = sys.env.getOrElse("SG_TOKEN_SECRET", "magnets")

  @Singleton @Provides
  def provideTokenService(): Tokenizer =
    new Tokenizer(secret)
}
