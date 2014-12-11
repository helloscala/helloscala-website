package com.helloscala.platform

import com.helloscala.platform.driver.MyJdbcDriver
import com.helloscala.platform.model.entity.Entities
import com.helloscala.platform.util.Tools
import org.scalatest.{Matchers, BeforeAndAfterAll, Suite}

trait MyFunSpec extends BeforeAndAfterAll with Matchers {
  this: Suite =>

  val conf = Tools.getConfig
  val entities = new Entities(conf.sql_db, MyJdbcDriver.is)

  println(conf + "\n")
}
