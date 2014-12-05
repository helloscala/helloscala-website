package com.helloscala.platform.model.entity

import com.helloscala.platform.MyFunSpec
import com.helloscala.platform.common.SuperId
import com.helloscala.platform.model.{DocumentModel, UserModel}
import com.helloscala.platform.util.Y
import org.scalatest.FunSpec

class DocumentModelTest extends FunSpec with MyFunSpec {
  val docModel = DocumentModel(entities)
  val userModel = UserModel(entities)

  describe("DocumentModel") {
    var docId: SuperId = null
    it("init datas") {
      val user = userModel.findOneById(12L).get
      val content = """    import scala.annotation.tailrec
                      |    import scalaz._
                      |    import Ordering._
                      |
                      |    val data = Vector(1, 2, 3, 4, 6, 9)
                      |
                      |    def binary_search[T: Order](x: T, d: Vector[T]) = {
                      |      @tailrec
                      |      def helper(min: Int, max: Int): Int = {
                      |        if (max <= min) -(min + 1)
                      |        else {
                      |          val middle = (min + max) / 2
                      |          implicitly[Order[T]].apply(x, d(middle)) match {
                      |            case EQ => middle
                      |            case GT => helper(min + 1, max)
                      |            case LT => helper(min, max - 1)
                      |          }
                      |        }
                      |      }
                      |      helper(0, d.size - 1)
                      |    }
                      |
                      |    assert(binary_search(3, data) == 2)
                      |    assert(binary_search(5, data) == -5)
                      |
                      |
                      | """.stripMargin
      val createdAt = Y.formatDateTimeMillis.parseDateTime("2013-12-06 16:20:51.972")
      val doc = MDocument(None, "二分查找算法", user.id.get, content,
        Some("scalaz版"),
        None, created_at = createdAt)

      val d = docModel.insert(doc, Nil)
      println(d)
      docId = d.id.get
    }

    it("find") {
      val doc = docModel.findOneById(docId).get
      println(doc.content)
    }
  }
}
