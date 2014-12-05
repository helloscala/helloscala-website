package com.helloscala.site.helper

import com.helloscala.platform.MyFunSpec
import com.helloscala.platform.common.SortAts
import com.helloscala.platform.model.DocumentModel
import httl.Engine
import org.scalatest.FunSpec

class DocumentHelperTest extends FunSpec with MyFunSpec {
  val engine = Engine.getEngine
  val helper = new DocumentHelper(conf, engine)
  val documentModel = DocumentModel(entities)

  describe("DocumentHelper") {
    it("makeHtml") {
      val doc = documentModel.pager(1, None, SortAts.Desc).items.head
      //      val out = new ByteArrayOutputStream()
      //      helper.makeHtml(doc, out)
      //      val s = Source.fromBytes(out.toByteArray)
      //      s.getLines() foreach println
      //      out.close()
      helper.makeHtml(doc)
    }
  }
}
