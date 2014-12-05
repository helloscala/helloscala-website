package com.helloscala.site.bootstrap

import com.helloscala.platform.util.Tools

object Boot {
  def main(args: Array[String]): Unit = {
    ServerActor.start(Tools.getConfig)
  }
}
