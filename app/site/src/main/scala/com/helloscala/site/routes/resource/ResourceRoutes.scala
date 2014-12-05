package com.helloscala.site.routes.resource

import java.io.File

import com.helloscala.platform.common.SuperId
import com.helloscala.platform.util.Tools
import com.helloscala.site.routes.helper.MyRouteHelper

trait ResourceRoutes {
  this: MyRouteHelper =>

  private val _console404Html = new File(systemContext.conf.server.localWebapp + "/console/_404.html")
  private val _404Html = new File(systemContext.conf.server.localWebapp + "/404.html")

  val resourceRoutes =
    pathEndOrSingleSlash {
      getFromFile {
        new File(systemContext.conf.server.localWebapp + "/index.html")
      }
    } ~
      path(Segment) { segment =>
        getFromFile {
          val p = if (Tools.indexHtml.contains(segment)) "index.html" else segment
          new File(systemContext.conf.server.localWebapp + "/" + p)
        }
      } ~
      staticPaths("assets") ~
      staticPaths("console", _console404Html) ~
      staticPaths("page") ~
      staticPaths("upload") ~
      pathPrefix("document") {
        path(Segment) { segment =>
          getFromFile {
            val file = if (SuperId.isValid(segment)) {
              new File(systemContext.conf.server.localWebapp + "/document/" + segment + ".html")
            } else {
              val file = new File(systemContext.conf.server.localWebapp + "/document/" + segment)
              if (file.exists() && file.canRead) {
                file
              } else {
                systemContext.documentService.findIdBySlug(segment).map(id =>
                  new File(systemContext.conf.server.localWebapp + "/document/" + id + ".html")) getOrElse
                  _404Html
              }
            }
            logger.debug("document file:\n" + file)
            file
          }
        }
      }

  private def staticPaths(dir: String, _404: File = _404Html) = pathPrefix(dir) {
    path(Rest) { p =>
      getFromFile {
        val file = new File(systemContext.conf.server.localWebapp + "/" + dir + "/" +
          (if (Tools.indexHtml.contains(p)) "index.html" else p))

        if (file.exists()) file else _404
      }
    }
  }
}
