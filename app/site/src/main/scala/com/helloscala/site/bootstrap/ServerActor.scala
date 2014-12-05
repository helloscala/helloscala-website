package com.helloscala.site.bootstrap

import java.util.concurrent.TimeUnit

import akka.actor._
import akka.io.{IO, Tcp}
import akka.pattern.ask
import akka.util.Timeout
import com.helloscala.platform.util.{Conf, Tools}
import com.helloscala.site.routes.RoutesActor
import com.helloscala.site.services.SystemContext
import com.typesafe.scalalogging.StrictLogging
import spray.can.Http

import scala.util.{Failure, Success}

case object Start

case object ServerShutdown

object ServerActor {
  def start(conf: Conf): Unit = {
    ActorSystem(conf.meta.id).actorOf(Props(classOf[ServerActor], conf), name = Tools.ServerActorName) ! Start
  }
}

class ServerActor(val conf: Conf) extends Actor with StrictLogging {

  import context.dispatcher

  implicit val timeout = Timeout(conf.server.timeout, TimeUnit.MILLISECONDS)

  val systemContext = new SystemContext(conf, context.system)
  val routeActor = context.actorOf(RoutesActor.props(conf, systemContext), Tools.RoutesActorName)
  val httpActor = IO(Http)(context.system)

  def receive: Receive = {
    case Start =>
      httpActor ? Http.Bind(routeActor, conf.server.interface, conf.server.port) onComplete {
        case Success(resp) =>
          resp match {
            case b: Http.Bound =>
              println("成功绑定到：" + b.localAddress)
            case Tcp.CommandFailed(b: Http.Bind) ⇒
              println("绑定到：" + b.endpoint + "失败")
              self ! ServerShutdown
          }

        case Failure(e) =>
          e.printStackTrace()
          self ! ServerShutdown
      }

    case ServerShutdown =>
      logger.info("ServerShutdown，开始关闭……")
      routeActor ! PoisonPill
      context become shuttingDown
  }

  /**
   * 关闭流程：httpActor -> systemContext-> context.system
   * TODO 设计关闭流程
   */
  def shuttingDown: Receive = {
    case Terminated(ref) if ref.path == routeActor.path =>
      context.stop(self)
      context.system.shutdown()

    case Terminated(ref) =>
      println("Terminated(ref): " + ref.path.toString)

    case msg =>
      sender() ! "service unavailable, shutting down"
  }
}
