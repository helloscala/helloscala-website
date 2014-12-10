package com.helloscala.platform.model

import com.helloscala.platform.common.SortAt
import com.helloscala.platform.model.entity.{Entities, MUser, MUserPassword}
import com.helloscala.platform.util._
import yangbajing.common.MessageException

import scala.util.Random

case class UserPageResponse(items: List[MUser], count: Long, params: IdParams[Long]) extends PageIdDataResponse[Long, MUser]

class UserModel private(val entities: Entities) extends BaseModel {

  import entities._
  import entities.driver.simple._

  override type ID = Option[Long]
  override type PK = Long
  override type Entity = MUser

  def update(user: Entity): Entity = db withTransaction { implicit ss =>
    val ret = tUser.filter(_.id === user.id).update(user)
    Tools.require(ret == 1, StatusMsgs.UpdateFailure)
    user
  }

  def removeById(id: Long): Boolean = db withTransaction { implicit ss =>
    tUser.filter(_.id === id).delete == 1
  }

  def insert(user: MUser, md5Pass: String): MUser = db withTransaction { implicit ss =>
    val userId = tUser returning tUser.map(_.id) += user

    tUserPassword += UserModel.createPasswordByMd5(userId, md5Pass)

    user.copy(id = Some(userId))
  }

  def findOneById(id: Long): Option[MUser] = db withSession { implicit ss =>
    tUser.filter(_.id === id).firstOption
  }

  def updatePassword(id: PK, up: MUserPassword): Boolean = db withTransaction { implicit ss =>
    tUserPassword.filter(_.id === id).update(up) == 1
  }

  def findOne(account: String, md5Pass: String): Option[MUser] = db withSession { implicit ss =>
    def filterAccount(t: TableUser) = {
      if (Y.emailValidate(account)) t.email === account
      else t.username === account
    }

    val q = for {
      u <- tUser if filterAccount(u)
      up <- tUserPassword if up.id === u.id
    } yield (u, up)

    q.firstOption match {
      case Some((u, up)) =>
        if (UserModel.matchPassword(up, md5Pass)) Some(u)
        else throw MessageException(StatusMsgs.AccountAuthError)
      case None => None
    }
  }


  def pagerAll(limit: Int, at: Option[Long], page: Option[Int], sortAt: SortAt) = db withSession { implicit ss =>
    val q = tUser.sortBy(t => pagerSort(t.id, sortAt)).filter { t =>
      Seq(
        if (page.isDefined) None else at.map(pagerFilter(tUser.baseTableRow, _, sortAt))
      )
      at match {
        case Some(atId) => t.id < atId
        case _ => LiteralColumn(true)
      }
    }.take(limit)

    val items = page match {
      case Some(p) => q.drop((p * limit + 1) / limit).list
      case None => q.list
    }

    val (minAt, maxAt) = minMaxIdLong(tUser.baseTableRow)

    val newerId = if (items.isEmpty || minAt == items.head.id) None else Some(items.head.id.get)

    val olderId = if (items.isEmpty || minAt == items.last.id) None else Some(items.last.id.get)

    UserPageResponse(items, count(tUser.baseTableRow), IdParams(limit, newerId, olderId, page))
  }
}

object UserModel {
  def matchPassword(p: MUserPassword, md5Pass: String) = {
    p.password == Y.ySha1(p.salt + md5Pass)
  }

  def createPassword(id: Long, password: String) = {
    createPasswordByMd5(id, Y.yMd5(password))
  }

  def createPasswordByMd5(id: Long, md5Password: String) = {
    val salt = Random.nextString(_set(Random.nextInt(10)))

    val password = Y.ySha1(salt + md5Password)
    MUserPassword(id, salt, password)
  }

  def apply(entities: Entities) = new UserModel(entities)

  private val _set = (8 to 17).toArray
}
