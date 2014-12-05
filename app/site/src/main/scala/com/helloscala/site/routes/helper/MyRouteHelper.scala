package com.helloscala.site.routes.helper


import com.helloscala.platform.util.{StatusMsgs, Tools}
import com.helloscala.site.services.SystemContextHelper
import com.helloscala.site.helper.MyJson4sSupport
import yangbajing.common.MessageException

import scala.concurrent.{Await, ExecutionContextExecutor}

trait MyRouteHelper extends SystemContextHelper with MyHttpHelper with MyJson4sSupport {
  protected implicit def __dispatcher: ExecutionContextExecutor = actorRefFactory.dispatcher

  protected def extractAccount = extract { ctx =>
    ctx.request.headers.find(_.name.equalsIgnoreCase(Tools.HELLOSCALE_SESSION_TOKEN)) match {
      case Some(header) =>
        val token = header.value
        val f = systemContext.sessionService.querySession(token)(__dispatcher, systemContext.timeout)
        Await.result(f, systemContext.timeout.duration).account

      case None =>
        throw MessageException(StatusMsgs.SessionTokenNotExists)
    }
  }

  protected val extractToken = extract { ctx =>
    ctx.request.headers.find(_.name.equalsIgnoreCase(Tools.HELLOSCALE_SESSION_TOKEN)) match {
      case Some(header) =>
        header.value

      case None =>
        throw MessageException(StatusMsgs.SessionTokenNotExists)
    }
  }
}
