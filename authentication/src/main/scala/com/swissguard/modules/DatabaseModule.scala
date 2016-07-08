package com.swissguard.modules

import javax.inject.Singleton

import com.google.inject.Provides
import com.twitter.finagle.exp.mysql.Client
import com.twitter.finagle.exp.Mysql
import com.twitter.inject.TwitterModule

object DatabaseModule extends TwitterModule{
  val server = sys.env.getOrElse("SG_DB_HOST","localhost:33061")
  val username = sys.env.getOrElse("SG_DB_USERNAME","mysql")
  val password = sys.env.getOrElse("SG_DB_PASSWORD","mysql")
  val connectionUrl = s"jdbc:postgresql://$server/swissguard?user=$username&password=$password"


  @Singleton @Provides
  def provideMySqlDatabase(): Client = {
    Mysql.client
      .withCredentials(username, password)
      .withDatabase("swissguard")
      .newRichClient(s"inet!$server")
  }

}
