package com.helloscala.platform.model.entity

import com.helloscala.platform.common.SuperId
import com.helloscala.platform.util.Y
import org.joda.time.DateTime

/**
 * 文章
 * Created by Yang Jing on 2014-11-24.
 */
case class MDocument(id: Option[SuperId],
                     title: String,
                     author: Long,
                     content: String,
                     description: Option[String],
                     slug: Option[String],

                     allow_anonymous: Boolean = false,

                     /**
                      * @see MTag
                      */
                     tags: List[Long] = Nil,

                     /**
                      * username: "缓存用户名",
                      * nick: "缓存用户昵称"
                      * tag_{id}: "缓存Tag（多个Tag以tag_{id}键保存）",
                      */
                     attrs: Map[String, String] = Map(),
                     created_at: DateTime = DateTime.now()) extends BaseEntity[Option[SuperId]] {
  //  def getTitle() = title
  //
  //  def getContent() = content
  def getJDocUri = s"/document/${created_at.getYear}/${"%2d".format(created_at.getMonthOfYear)}/${id.get}.html"
}

case class MDocumentComment(id: Option[SuperId],
                            document_id: SuperId,
                            quote_id: Option[SuperId],
                            user_id: Option[Long],
                            anonymous_email: Option[String],
                            anonymous_nick: Option[String],
                            content: String,
                            created_at: DateTime = DateTime.now) extends BaseEntity[Option[SuperId]]
