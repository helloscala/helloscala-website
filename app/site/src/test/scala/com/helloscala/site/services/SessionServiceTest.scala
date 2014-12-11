package com.helloscala.site.services

import com.helloscala.platform.util.Y
import com.helloscala.platform.util.Y.json4sDefaultFormats
import com.helloscala.site.MySiteFunSpec
import com.helloscala.site.helper.HttpClient
import org.scalatest.FunSpec

class SessionServiceTest extends FunSpec with MySiteFunSpec {
  val client = new HttpClient(conf)

  describe("SessionService") {
    it("register") {
      val f = client.registerUser("test@helloscala.com", Y.yMd5("scalahello"))
      dump(f)
    }

    it("query") {
      val f = client.sessionQuery("8eb771d8-5548-428d-86f8-ab309df9e84e")
      dump(f)
    }
  }

}
