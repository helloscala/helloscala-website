package com.helloscala.site.routes.helper

import java.sql.SQLException

import akka.pattern.AskTimeoutException
import com.helloscala.platform.util.StatusMsgs
import com.helloscala.site.helper.MyJson4sSupport
import com.typesafe.scalalogging.StrictLogging
import spray.http.StatusCodes
import spray.routing.{HttpService, Route}
import yangbajing.common.{MessageException, TDataMessage, TRespMessage, TStatusMessage}

import scala.util.{Failure, Success, Try}

trait MyHttpHelper extends HttpService with MyJson4sSupport with StrictLogging {
  def defaultComplete: PartialFunction[Try[Any], Route] = {
    case Success(v) =>
      v match {
        case opt: Option[_] =>
          opt match {
            case Some(resp) => completeMessage(resp)
            case None => complete(StatusMsgs.DataNotFound)
          }
        case Right(resp) => completeMessage(resp)
        case Left(msg: TStatusMessage) => complete(msg)
        case _ => completeMessage(v)
      }
    case Failure(e) =>
      defaultFailureComplete(e)
  }

  //  def completeMessage[T: Marshaller](v: T) = {
  //    complete(v)
  //  }

  def completeMessage(v: Any) = {
    v match {
      case msg: TDataMessage => complete(msg)
      case msg: TStatusMessage => complete(msg)
      case msg: TRespMessage => complete(msg)
      //      case _ => throw new Error("不是TDataMessage、TRespMessage或TErrorMessage")
    }
  }

  def defaultFailureComplete(e: Throwable) = e match {
    case MessageException(msg) =>
      complete(msg)

    case ex: SQLException =>
      logger.warn("timeout exception", e)
      val se = ex.getNextException
      if (se ne null)
        complete(StatusMsgs.sqlError("[" + se.getErrorCode + "]: " + se.getLocalizedMessage))
      else
        complete(StatusMsgs.sqlError(ex.getLocalizedMessage))

    case _: AskTimeoutException =>
      logger.warn("timeout exception", e)
      complete(StatusMsgs.RequestTimeout)

    case _ =>
      logger.error("throwable", e)
      complete(StatusCodes.InternalServerError, StatusMsgs.error(e.getLocalizedMessage))
  }
}
