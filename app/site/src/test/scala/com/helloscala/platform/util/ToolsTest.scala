package com.helloscala.platform.util

import com.helloscala.platform.util.Y.json4sDefaultFormats
import org.scalatest.FunSpec

class ToolsTest extends FunSpec {
  describe("Tools") {
    it("conf") {
      val conf = Tools.getConfig
      println(conf)
      println(Y.json4sToStringPretty(conf))
    }
  }
}
