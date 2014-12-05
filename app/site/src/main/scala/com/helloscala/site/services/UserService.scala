package com.helloscala.site.services

import com.helloscala.platform.common.{SortAts, SortAt}
import com.helloscala.platform.model.{UserPageResponse, UserModel}
import com.helloscala.platform.model.entity.{MUser, Entities}
import com.helloscala.platform.util.{StatusMsgs, Conf}

import scala.concurrent.{ExecutionContext, Future}

/**
 * 用户服务
 * Created by Yang Jing on 2014-11-25.
 */
class UserService(conf: Conf, entities: Entities) {
  def findAll(limit: Int, at: Option[Long], page: Option[Int], sortAt: Option[SortAt]
               )(implicit ec: ExecutionContext): Future[UserPageResponse] = Future {
    userModel.pagerAll(limit, at, page, sortAt.getOrElse(SortAts.Desc))
  }

  def insert(user: MUser, md5Pass: String)(implicit ec: ExecutionContext): Future[MUser] = Future {
    userModel.insert(user, md5Pass)
  }

  def removeById(id: Long)(implicit ec: ExecutionContext) = Future {
    if (userModel.removeById(id)) StatusMsgs.Ok else StatusMsgs.RemoveFailure
  }

  def update(user: MUser)(implicit ec: ExecutionContext) = Future {
    userModel.update(user)
  }

  def findOneById(id: Long)(implicit ec: ExecutionContext): Future[Option[MUser]] = Future {
    userModel.findOneById(id)
  }

  val userModel = UserModel(entities)
}

case class ReqUser(user: MUser, md5_pass: String)
