package com.helloscala.site.routes.api.data

import com.helloscala.platform.util.StatusMsgs
import com.helloscala.site.routes.helper.MyRouteHelper

trait ApiDataRoutes {
  this: MyRouteHelper =>
  val apiDataRotue = pathPrefix("data") {
    complete(StatusMsgs.success("api data route"))
  }
}
