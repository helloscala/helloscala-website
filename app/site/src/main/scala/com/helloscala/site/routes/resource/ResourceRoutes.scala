package com.helloscala.site.routes.resource

import java.io.File

import com.helloscala.platform.common.{SortAts, SuperId}
import com.helloscala.platform.util.Tools
import com.helloscala.site.routes.helper.MyRouteHelper
import org.joda.time.DateTime
import spray.http.MediaTypes

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
      staticPaths("page") ~
      staticPaths("upload") ~
      pathPrefix("console") {
        path(Rest) { p =>
          getFromFile {
            val file = new File(systemContext.conf.server.localWebapp + "/console/" +
              (if (p.length == 0 || p.charAt(0) != '_') "index.html" else p))
            //            println(file)
            file
          }
        }
      } ~
      pathPrefix("document") {
        pathEnd {
          get {
            parameters('limit.as[Int].?, 'at.as[Long].?, 'sort.as[String].?) { (limit, at, sort) =>
              val resp = systemContext.documentService.listHtml(limit.getOrElse(15), at.map(new DateTime(_)),
                sort.flatMap(SortAts.extractionOpt).getOrElse(SortAts.Desc))
              complete(resp)
            }
          }
        } ~
          path(Rest) { p =>
            getFromFile {
              val file = p.split('/') match {
                case Array(year, month, segment) =>
                  val idOrSlug = segment.split('.') match {
                    case Array(v, "html") => v
                    case _ => segment
                  }

                  val dir = systemContext.conf.server.localWebapp + s"/document/$year/$month"
                  (if (SuperId.isValid(idOrSlug)) Some(SuperId(idOrSlug))
                  else systemContext.documentService.findIdBySlug(idOrSlug)) match {
                    case Some(id) =>
                      val f = new File(dir, id + ".html")
                      if (!f.exists) {
                        systemContext.documentService.findOneById(id).foreach(systemContext.documentService.helper.makeDetail)
                      }
                      f

                    case _ =>
                      _404Html
                  }

                //                  if (SuperId.isValid(segment)) {
                //                    new File(dir, segment + ".html")
                //                  } else {
                //                    val file = new File(dir, segment)
                //                    if (file.exists()) {
                //                      file
                //                    } else {
                //                      segment.split('.') match {
                //                        case Array(id, suffix) =>
                //                          systemContext.documentService.findOne(id) match {
                //                            case Some(doc) =>
                //                              systemContext.documentService.helper.makeHtml(systemContext.documentService.findOne(segment).get)
                //                              new File(dir, id + ".html")
                //
                //                            case _ =>
                //                              _404Html
                //                          }
                //
                //                        case _ =>
                //                          systemContext.documentService.findIdBySlug(segment) match {
                //                            case Some(id) =>
                //                              new File(dir, id + ".html")
                //
                //                            case _ =>
                //                              _404Html
                //                          }
                //                      }
                //
                //                    }
                //                  }

                case _ =>
                  _404Html
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
        //        println(file)
        if (file.exists()) file else _404
      }
    }
  }
}
