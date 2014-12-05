package com.helloscala.site.routes.api

import com.helloscala.site.routes.api.console.ApiConsoleRoutes
import com.helloscala.site.routes.api.data.ApiDataRoutes
import com.helloscala.site.routes.api.session.SessionRoutes
import com.helloscala.site.routes.api.system.ApiSystemRoutes
import com.helloscala.site.routes.api.upload.ApiUploadRoutes
import com.helloscala.site.routes.helper.MyRouteHelper

trait ApiRoutes
  extends ApiDataRoutes
  with ApiConsoleRoutes
  with ApiUploadRoutes
  with ApiSystemRoutes
  with SessionRoutes {
  this: MyRouteHelper =>

  val apiRoutes = pathPrefix("api") {
    apiDataRotue ~
      apiConsoleRoute ~
      apiUploadRoute ~
      apiSessionRoute ~
      apiSystemRoute
  }
}
