package com.helloscala.site.routes.api.system

import java.util.concurrent.TimeUnit

import com.helloscala.platform.util.StatusMsgs
import com.helloscala.site.routes.helper.MyRouteHelper

import scala.concurrent.duration.Duration


trait ApiSystemRoutes {
  this: MyRouteHelper =>

  val apiSystemRoute = pathPrefix("system") {
    path("shutdown") {
      post {
        complete {
          systemContext.scheduler().scheduleOnce(Duration(2, TimeUnit.SECONDS)) {
            systemContext.shutdown()
          }
          StatusMsgs.success("shutdown 2s....")
        }
      }
    }
  }

}
