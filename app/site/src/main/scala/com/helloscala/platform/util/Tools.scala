package com.helloscala.platform.util

import yangbajing.common.MessageException

object Tools extends ConfHelper {
  final val RunMode = Option(System.getenv("SITE_RUN_MODE")) getOrElse System.getProperty("site.run_mode", "debug")
  final val HELLOSCALE_SESSION_TOKEN = "HELLOSCALA-SESSION-TOKEN"
  final val RoutesActorName = "routes"
  final val ServerActorName = "server"

  final val indexHtml = Set("", "index", "index.httl", "index.html")

  def getConfig: Conf = getConfig("site-" + RunMode)

  def akkaLocalPath(systemId: String, userPath: String): String =
    s"akka://$systemId/user/$userPath"

  def require(be: Boolean, msg: StatusMsg): Unit = {
    if (!be) throw MessageException(msg)
  }

  def require(be: Boolean, msg: String): Unit = {
    require(be, StatusMsgs.error(msg))
  }
}
