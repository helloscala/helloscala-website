package com.helloscala.site.helper

import java.util.Properties

import com.helloscala.platform.MyFunSpec
import com.helloscala.platform.common.SortAts
import com.helloscala.platform.model.DocumentModel
import httl.Engine
import org.scalatest.FunSpec

class DocumentHelperTest extends FunSpec with MyFunSpec {
  val engine = {
    val props = new Properties()
    props.setProperty("loaders", "httl.spi.loaders.FileLoader")
    props.setProperty("template.directory", conf.server.localWebapp)
    Engine.getEngine(props)
  }
  val helper = new DocumentHelper(conf, entities, engine)
  val documentModel = DocumentModel(entities)

  describe("DocumentHelper") {
    it("makeDocumentDetail") {
      documentModel.findAll().foreach { doc =>
        helper.makeHtml(doc)
      }
    }

    it("makeDocumentList") {
      val pager = documentModel.pager(15, None, SortAts.Desc)
      pager.items.nonEmpty shouldBe true
      helper.makeDocumentList(pager, System.out)
    }
  }
}
