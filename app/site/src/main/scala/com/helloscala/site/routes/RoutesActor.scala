package com.helloscala.site.routes

import java.io.File

import akka.actor.Props
import com.helloscala.platform.util.{Conf, HttpResponses, StatusMsgs}
import com.helloscala.site.beans.Page404Bean
import com.helloscala.site.services.SystemContext
import com.typesafe.scalalogging.StrictLogging
import spray.http._
import spray.routing._
import yangbajing.common.MessageException

object RoutesActor {
  def props(conf: Conf, systemContext: SystemContext) = Props(new RoutesActor(conf, systemContext))
}

class RoutesActor(val conf: Conf, val systemContext: SystemContext) extends HttpServiceActor with Routes with StrictLogging {

  override def receive = handleTimeouts orElse runRoute(routes)

  def handleTimeouts: Receive = {
    case Timedout(x: HttpRequest) => sender ! HttpResponses.RequestTimeoutResponse
  }

  protected implicit val __rejectHandler = RejectionHandler {
    case Nil =>
      logger.debug("NotFound")
      complete(systemContext.httlService.page404(Page404Bean("页面不存在！！！")))

    case bean@(MissingQueryParamRejection(_) :: _) =>
      val params = bean.asInstanceOf[List[MissingQueryParamRejection]]
      complete(StatusMsgs.queryParamError(params.map(_.parameterName).mkString(", ")))

    case bean@(MissingFormFieldRejection(_) :: _) =>
      val fields = bean.asInstanceOf[List[MissingFormFieldRejection]]
      complete(StatusMsgs.queryParamError(fields.map(_.fieldName).mkString(", ")))

    //    case MissingHeaderRejection(Tools.HELLOSCALE_SESSION_TOKEN) :: _ =>
    //      complete(StatusMsgs.SessionTokenNotExists)
    //
    //    case MalformedHeaderRejection(headerName, errorMsg, cause) :: _ =>
    //      complete(StatusMsgs.SessionTokenInvalid)

    case list =>
      val msg = list.toString()
      logger.debug(msg)
      complete(StatusMsgs.rejection(msg))

  }

  protected implicit val __exceptionHandler = ExceptionHandler {
    case MessageException(msg) => complete(msg)
    case e: Exception => complete(StatusCodes.InternalServerError, e.getLocalizedMessage)
  }

}
