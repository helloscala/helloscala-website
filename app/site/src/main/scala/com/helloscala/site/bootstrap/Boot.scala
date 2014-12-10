package com.helloscala.site.bootstrap

import akka.actor.{Props, ActorSystem}
import com.helloscala.platform.util.Tools

import scala.io.StdIn

object Boot {
  @volatile var be = true

  def main(args: Array[String]): Unit = {
    val conf = Tools.getConfig
    val system = ActorSystem(conf.meta.id)
    system.actorOf(Props(classOf[ServerActor], conf), name = Tools.ServerActorName) ! Start
    if (Tools.RunMode == "debug") {
      do {
        println("输入：[shutdown] 指令退出系统。")
        val line = StdIn.readLine()
        if (line == "shutdown") {
          system.actorSelection(Tools.akkaLocalPath(conf.meta.id, Tools.ServerActorName)) ! ServerShutdown
          be = false
        }
      } while (be)
    }
  }
}
