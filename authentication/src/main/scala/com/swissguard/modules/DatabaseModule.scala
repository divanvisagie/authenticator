package com.swissguard.modules

import javax.inject.Singleton
import com.google.inject.Provides
import com.twitter.inject.TwitterModule

object DatabaseModule extends TwitterModule{
  import slick.driver.PostgresDriver.api.Database
  val server = sys.env.getOrElse("SG_PG_INET","localhost:54321")
  val username = sys.env.getOrElse("SG_PG_USERNAME","postgres")
  val password = sys.env.getOrElse("SG_PG_PASSWORD","postgres")
  val connectionUrl = s"jdbc:postgresql://$server/swissguard?user=$username&password=$password"

  @Singleton @Provides
  def provideDatabase(): Database =
    Database.forURL(connectionUrl, driver = "org.postgresql.Driver")
}
