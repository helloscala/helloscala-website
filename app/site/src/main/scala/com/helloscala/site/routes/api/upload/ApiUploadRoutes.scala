package com.helloscala.site.routes.api.upload

import com.helloscala.site.routes.helper.MyRouteHelper
import spray.http.MultipartFormData

trait ApiUploadRoutes {
  this: MyRouteHelper =>

  val apiUploadRoute = pathPrefix("upload") {
    path("image") {
      post {
        entity(as[MultipartFormData]) { data =>
          onComplete(systemContext.imageService.saveImage(data))(defaultComplete)
        }
      }
    }
  }
}
