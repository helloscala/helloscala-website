package com.helloscala.platform.model

import java.sql.SQLException

import com.helloscala.platform.common.{SortAt, SortAts, SuperId}
import com.helloscala.platform.model.entity.{Entities, MDocument, MDocumentComment}
import com.helloscala.platform.util.{Params, PageDataResponse, StatusMsgs, Tools}
import org.joda.time.DateTime

/**
 * 文章
 * Created by Yang Jing on 2014-11-24.
 */
object DocumentModel {
  def apply(entities: Entities) = new DocumentModel(entities)
}

class DocumentModel private(val entities: Entities) extends BaseModel {
  override type ID = Option[SuperId]
  override type Entity = MDocument
  override type PK = SuperId


  import entities._
  import entities.driver.simple._

  def findIdBySlug(slug: String): Option[SuperId] = db withSession { implicit ss =>
    tDocument.filter(_.slug === slug).map(_.id).firstOption
  }

  def insert(document: Entity, comments: Seq[MDocumentComment]): Entity = {
    val t = if (document.id.isEmpty) document.copy(id = Some(SuperId())) else document

    if (comments.nonEmpty)
      Tools.require(comments.forall(_.document_id == document.id.get), StatusMsgs.error("评论引用文章ID无效"))

    val cs = comments.map(c => if (c.id.isEmpty) c.copy(id = Some(SuperId())) else c)

    db withTransaction { implicit ss =>
      tDocument += t
      tDocumentComment ++= cs
    }
    t
  }

  def pagerComments(documentId: PK, limit: Int, at: Option[DateTime], pagerAt: SortAt): DocumentCommentPagerResponse =
    db withSession { implicit ss =>
      val q = tDocumentComment.sortBy(t => pagerSort(t.created_at, pagerAt)).filter { t =>
        Seq(
          at.map(time => pagerAt match {
            case SortAts.Asc => t.created_at > time
            case SortAts.Desc => t.created_at < time
          })
        ).flatten.reduceOption(_ && _).getOrElse(LiteralColumn(true))
      }

      val items = q.drop(limit).list
      val (minAt, maxAt) = minMaxCreatedAt(tDocumentComment.baseTableRow)

      val newerTime =
        if (items.isEmpty || maxAt.exists(_.getTime == items.head.created_at.getMillis)) None
        else Some(items.head.created_at.getMillis)

      val olderTime =
        if (items.isEmpty || minAt.exists(_.getTime == items.last.created_at.getMillis)) None
        else Some(items.last.created_at.getMillis)

      DocumentCommentPagerResponse(items, count(tDocumentComment.baseTableRow), Params(limit, newerTime, olderTime, None))
    }

  def pager(limit: Int, at: Option[DateTime], pagerAt: SortAt): DocumentPagerResponse =
    db withSession { implicit ss =>
      val q = tDocument.sortBy(t => pagerSort(t.created_at, pagerAt)).filter { t =>
        Seq(
          at.map(time => pagerAt match {
            case SortAts.Asc => t.created_at > time
            case SortAts.Desc => t.created_at < time
          })
        ).flatten.reduceOption(_ && _).getOrElse(LiteralColumn(true))
      }

      val items = q.drop(limit).list
      val (minAt, maxAt) = minMaxCreatedAt(tDocument.baseTableRow)

      val newerTime =
        if (items.isEmpty || maxAt.exists(_.getTime == items.head.created_at.getMillis)) None
        else Some(items.head.created_at.getMillis)

      val olderTime =
        if (items.isEmpty || minAt.exists(_.getTime == items.last.created_at.getMillis)) None
        else Some(items.last.created_at.getMillis)

      DocumentPagerResponse(items, count(tDocument.baseTableRow), Params(limit, newerTime, olderTime, None))
    }

  def updateComments(comments: MDocumentComment*): Seq[Int] = {
    Tools.require(comments.forall(_.id.isDefined), StatusMsgs.error("评论ID无效"))
    db withTransaction { implicit ss =>
      comments.map { c =>
        tDocumentComment.filter(t => t.id === c.id).update(c)
      }
    }
  }

  def insertComments(comments: Seq[MDocumentComment]): List[MDocumentComment] = {
    val cs = comments.map(c => if (c.id.isEmpty) c.copy(id = Some(SuperId())) else c)
    db withTransaction { implicit ss =>
      tDocumentComment ++= cs
    }
    cs.toList
  }

  def update(document: Entity): Boolean = db withTransaction { implicit ss =>
    val result = tDocument.filter(_.id === document.id).update(document)
    if (result != 1) throw new SQLException("更新文章失败")
    else true
  }

  def findOne(idOrSlug: String): Option[Entity] = {
    if (SuperId.isValid(idOrSlug)) findOneById(SuperId(idOrSlug)) else findOneBySlug(idOrSlug)
  }

  def findOneById(id: PK): Option[Entity] = db withSession { implicit ss =>
    tDocument.filter(_.id === id).firstOption
  }

  def findOneBySlug(slug: String): Option[Entity] = db withSession { implicit ss =>
    tDocument.filter(_.slug === slug).firstOption
  }
}

case class DocumentPagerResponse(items: List[MDocument], count: Long, params: Params) extends PageDataResponse[MDocument]

case class DocumentCommentPagerResponse(items: List[MDocumentComment], count: Long, params: Params) extends PageDataResponse[MDocumentComment]