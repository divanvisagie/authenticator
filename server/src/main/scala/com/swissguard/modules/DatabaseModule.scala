package com.swissguard.modules

import javax.inject.Singleton
import com.google.inject.Provides
import com.twitter.inject.TwitterModule

object DatabaseModule extends TwitterModule{
  import slick.driver.PostgresDriver.api._
  val connectionUrl = "jdbc:postgresql://localhost:54321/swissguard?user=postgres&password=postgres"

  @Singleton @Provides
  def provideDatabase(): Database =
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver")
}
