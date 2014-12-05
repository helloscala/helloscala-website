package com.helloscala.site.services

import java.util.concurrent.TimeUnit

import akka.actor.{ActorRefFactory, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.helloscala.platform.model.entity.Entities
import com.helloscala.platform.model.{Account, UserModel}
import com.helloscala.platform.util.{Conf, StatusMsg, StatusMsgs}
import com.helloscala.site.services.session._
import com.typesafe.scalalogging.StrictLogging
import yangbajing.common.MessageException

import scala.concurrent.{ExecutionContext, Future}

/**
 * Session服务
 * Created by Yang Jing on 2014-11-19.
 */
class SessionService(conf: Conf, entities: Entities)(implicit actorRefFactory: ActorRefFactory) extends StrictLogging {
  private val userModel = UserModel(entities)
  private val sessionTimeout = Timeout(conf.server.session_timeout, TimeUnit.MILLISECONDS)
  private val master = actorRefFactory.actorOf(Props(classOf[SessionMaster], sessionTimeout), "session-master")

  def createSession(reqAuth: ReqAuth)(implicit ec: ExecutionContext, timeout: Timeout): Future[SessionAccount] = {
    logger.debug(s"createSession($reqAuth)")

    userModel.findOne(reqAuth.account, reqAuth.md5_pass) match {
      case Some(u) =>
        (master ? CreateSession(u.toAccount, timeout)).mapTo[SessionAccount]
      case None =>
        Future.failed(MessageException(StatusMsgs.AccountNotExists))
    }
  }

  def querySession(token: String)(implicit ec: ExecutionContext, timeout: Timeout): Future[SessionAccount] = {
    master.ask(QuerySession(token, timeout)).flatMap {
      case v: SessionAccount => Future.successful(v)
      case msg: StatusMsg => Future.failed(MessageException(msg))
    }
  }

  def removeSession(token: String)(implicit ex: ExecutionContext, timeout: Timeout): Future[StatusMsg] = {
    master.ask(RemoveSession(token)).mapTo[StatusMsg]
  }

  def resetSession(token: String, account: Account)(implicit ex: ExecutionContext, timeout: Timeout): Future[SessionAccount] = {
    master.ask(ResetSession(token, account, timeout)).flatMap {
      case v: SessionAccount => Future.successful(v)
      case msg: StatusMsg => Future.failed(MessageException(msg))
    }
  }

}

case class ReqAuth(account: String, md5_pass: String)