package com.helloscala.site.routes.api.console

import com.helloscala.platform.common.SortAts
import com.helloscala.platform.model.entity.MUser
import com.helloscala.site.routes.helper.MyRouteHelper
import com.helloscala.site.services.ReqUser

trait ApiConsoleRoutes {
  this: MyRouteHelper =>

  private val consoleUserRoute = pathPrefix("user") {
    pathEnd {
      get {
        parameters('limit.as[Int], 'at.as[Long].?, 'page.as[Int].?, 'pager_at.as[Int].?) { (limit, at, page, pagerAt) =>
          onComplete(systemContext.userService.findAll(limit, at, page, pagerAt.flatMap(SortAts.extractionOpt)))(defaultComplete)
        }
      } ~
        post {
          entity(as[ReqUser]) { bean =>
            onComplete(systemContext.userService.insert(bean.user, bean.md5_pass))(defaultComplete)
          }
        }
    } ~
      path(LongNumber) { uid =>
        get {
          onComplete(systemContext.userService.findOneById(uid))(defaultComplete)
        } ~
          put {
            entity(as[MUser]) { bean =>
              onComplete(systemContext.userService.update(bean))(defaultComplete)
            }
          } ~
          delete {
            onComplete(systemContext.userService.removeById(uid))(defaultComplete)
          }
      }
  }

  val apiConsoleRoute = pathPrefix("console") {
    //    consoleTagRoute ~
    //      consoleProvinceRoute ~
    //      consoleCityRoute ~
    //      consolePlaceRoute ~
    //      consoleCategoryRoute ~
    //      consoleMerchantRoute ~
    //      consoleStoreRoute ~
    //      consoleProductRoute ~
    //      consoleOrderRoute ~
    //      consoleClassifyRoute ~
    //      consoleUserGroupRoute ~
    //      consoleUserAddressRoute ~
    consoleUserRoute
  }

}
