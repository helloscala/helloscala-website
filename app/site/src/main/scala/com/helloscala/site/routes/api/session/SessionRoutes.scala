package com.helloscala.site.routes.api.session

import java.util.concurrent.TimeUnit

import akka.util.Timeout
import com.helloscala.site.routes.helper.MyRouteHelper
import com.helloscala.site.services.ReqAuth
import spray.http._

import scala.util.{Failure, Success}

trait SessionRoutes {
  this: MyRouteHelper =>

  private implicit val __timeout = Timeout(systemContext.conf.server.timeout, TimeUnit.MILLISECONDS)

  val apiSessionRoute = pathPrefix("session") {
    path("login") {
      post {
        entity(as[ReqAuth]) { reqAuth =>
          onComplete(systemContext.sessionService.createSession(reqAuth)) {
            case Success(value) =>
              setCookie(HttpCookie("session", content = value.token, path = None, domain = Some("localhost"))) {
                complete(value)
              }
            case Failure(e) =>
              defaultFailureComplete(e)
          }
        }
      }
    } ~
      path("query" / Segment) { token =>
        get {
          onComplete(systemContext.sessionService.querySession(token))(defaultComplete)
        }
      } ~
      path("logout" / Segment) { token =>
        delete {
          onComplete(systemContext.sessionService.removeSession(token))(defaultComplete)
        }
      }
  }
}
