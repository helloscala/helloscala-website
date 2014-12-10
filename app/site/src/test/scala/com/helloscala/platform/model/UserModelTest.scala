package com.helloscala.platform.model

import com.helloscala.platform.MyFunSpec
import com.helloscala.platform.util.Y
import org.scalatest.FunSpec

class UserModelTest extends FunSpec with MyFunSpec {
  val userModel = UserModel(entities)

  describe("UserModel") {
    it("findOne") {
      val u = userModel.findOne("yangbajing@gmail.com", Y.yMd5("striver"))
      println(u)
    }
  }
}
