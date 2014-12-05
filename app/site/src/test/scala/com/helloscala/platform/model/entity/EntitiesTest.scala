package com.helloscala.platform.model.entity

import com.helloscala.platform.MyFunSpec
import org.scalatest.FunSpec

import scala.slick.jdbc.StaticQuery

class EntitiesTest extends FunSpec with MyFunSpec {

  import entities.driver.simple._
  import entities._

  describe("Entities ddls") {
    it("drop tables") {
      ddls.dropStatements foreach { sql =>
        db withTransaction { implicit ss =>
          println(sql)
          StaticQuery.updateNA(sql).execute
        }
      }
    }

    it("create tables") {
      ddls.createStatements foreach { sql =>
        db withTransaction { implicit ss =>
          println(sql)
          StaticQuery.updateNA(sql).execute
        }
      }
    }
  }

  describe("Entities temproray") {
    it("create tables") {
      db withTransaction { implicit ss =>
        tTag.ddl.create
        tDocument.ddl.create
        tDocumentComment.ddl.create
      }
    }
  }
}
