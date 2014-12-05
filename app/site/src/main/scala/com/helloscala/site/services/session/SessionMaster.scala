package com.helloscala.site.services.session

import java.util.UUID
import java.util.concurrent.TimeUnit

import akka.actor.{Actor, Props, Terminated}
import akka.pattern.ask
import akka.util.Timeout
import com.helloscala.platform.common.Tick
import com.helloscala.platform.model.Account
import com.helloscala.platform.util.StatusMsgs
import com.typesafe.scalalogging.StrictLogging
import org.joda.time.DateTime
import yangbajing.common.TDataMessage

import scala.concurrent.duration.{Duration, FiniteDuration}

case class QuerySession(token: String, timeout: Timeout)

case class RemoveSession(token: String)

case class CreateSession(account: Account, timeout: Timeout)

case class ResetSession(token: String, account: Account, timeout: Timeout)

case class SessionAccount(token: String, account: Account) extends TDataMessage

/**
 * Session Master
 * Created by Yang Jing on 2014-11-19.
 */
class SessionMaster(sessionTimeout: Timeout) extends Actor with StrictLogging {

  import context.dispatcher

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    logger.debug(self.path + ": preStart()")
  }

  def receive = {
    case CreateSession(account, timeout) =>
      val token = UUID.randomUUID().toString
      context.actorOf(Props(classOf[SessionWorker], sessionTimeout, account), name = token)
      sender ! SessionAccount(token, account)

    case bean@RemoveSession(token) =>
      context.child(token) match {
        case Some(worker) =>
          //          worker ! PoisonPill
          //          val doSender = sender()
          //          worker.ask(bean).onSuccess { case v => doSender ! v}
          context.stop(worker)
          sender() ! StatusMsgs.Ok

        case None =>
          sender() ! StatusMsgs.SessionTokenNotExists
      }

    case query@QuerySession(token, timeout) =>
      context.child(token) match {
        case Some(worker) =>
          val doSender = sender()
          worker.ask(query)(timeout).onSuccess { case v => doSender ! v}

        case None =>
          sender() ! StatusMsgs.SessionTokenNotExists
      }

    case bean@ResetSession(token: String, account: Account, timeout) =>
      context.child(token) match {
        case Some(worker) =>
          val doSender = sender()
          worker.ask(bean)(timeout).onSuccess { case v => doSender ! Right(SessionAccount(token, account))}

        case None =>
          sender() ! Left(StatusMsgs.SessionTokenNotExists)
      }

    case Terminated(worker) =>
      logger.info(s"${worker.path} 已终止")
  }
}

class SessionWorker(sessionTimeout: Timeout, tmpAccount: Account) extends Actor with StrictLogging {

  import context.dispatcher

  @volatile var _account: Account = tmpAccount
  @volatile var _stopTime = 0L

  @throws[Exception](classOf[Exception])
  override def preStart(): Unit = {
    _stopTime = System.currentTimeMillis() + sessionTimeout.duration.toMillis
    nextTick(sessionTimeout.duration)
  }

  def receive = {
    case QuerySession(token, _) =>
      if (token == self.path.name) {
        resetStopTime(System.currentTimeMillis())
        sender() ! SessionAccount(token, _account)
      } else {
        sender() ! StatusMsgs.SessionTokenNotExists
      }

    case Tick =>
      val curMillis = System.currentTimeMillis()
      if (curMillis < _stopTime) {
        nextTick(Duration(_stopTime - curMillis, TimeUnit.MILLISECONDS))
      } else {
        // 超时，退出
        logger.info(s"${self.path} 超时退出")
        context.stop(self)
      }

    case ResetSession(token, account, _) =>
      if (token == self.path.name) {
        resetStopTime(System.currentTimeMillis())
        _account = account
        sender() ! StatusMsgs.Ok
      } else {
        sender() ! StatusMsgs.SessionTokenNotExists
      }

    case RemoveSession(token) =>
      if (token == self.path.name) {
        context.stop(self)
        sender() ! StatusMsgs.Ok
      } else {
        sender() ! StatusMsgs.SessionTokenNotExists
      }
  }

  @inline
  private def resetStopTime(curMillis: Long): Unit = {
    _stopTime = curMillis + sessionTimeout.duration.toMillis
  }

  @inline
  private def nextTick(duration: FiniteDuration): Unit = {
    logger.debug(s"${self.path} 下一次Tick: ${DateTime.now().plus(duration.toMillis)}")
    context.system.scheduler.scheduleOnce(duration, self, Tick)
  }
}
