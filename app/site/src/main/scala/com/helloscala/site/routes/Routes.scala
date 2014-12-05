package com.helloscala.site.routes

import com.helloscala.site.routes.api.ApiRoutes
import com.helloscala.site.routes.helper.MyRouteHelper
import com.helloscala.site.routes.resource.ResourceRoutes
import spray.routing.{HttpService, Route}

trait Routes
  extends MyRouteHelper
  with ApiRoutes
  with ResourceRoutes {
  this: HttpService =>

  val routes: Route = {
    apiRoutes ~
      resourceRoutes
  }

}
