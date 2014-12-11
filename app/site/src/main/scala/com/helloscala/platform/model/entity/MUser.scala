package com.helloscala.platform.model.entity

import com.helloscala.platform.common.{SexTypes, SexType}
import com.helloscala.platform.model.Account
import org.joda.time.DateTime

case class MUser(id: Option[Long],
                 email: String,
                 username: Option[String],
                 nick: Option[String],
                 role_ids: List[Int],
                 sex: SexType = SexTypes.Unknown,

                 /**
                  * 头像
                  * @see MImage
                  */
                 portrait: Option[String] = None,

                 attrs: Map[String, String] = Map(),
                 created_at: DateTime = DateTime.now()) extends BaseEntity[Option[Long]] {
  def toAccount =
    Account(id.get, email, username, nick, role_ids, attrs)
}

case class MUserPassword(id: Long, salt: String, password: String)
