package com.helloscala.site.services

import java.io.{ByteArrayOutputStream, File}

import com.helloscala.platform.common.{SortAt, SuperId}
import com.helloscala.platform.model.DocumentModel
import com.helloscala.platform.model.entity.{MDocument, Entities}
import com.helloscala.platform.util.Conf
import com.helloscala.site.helper.DocumentHelper
import httl.Engine
import org.joda.time.DateTime
import spray.http._

/**
 * 文档服务
 * Created by Yang Jing on 2014-11-25.
 */
class DocumentService(conf: Conf, entities: Entities, httlEngine: Engine) {
  val helper = new DocumentHelper(conf, entities, httlEngine)

  def pager(limit: Int, at: Option[DateTime], sort: SortAt) = {
    documentModel.pager(limit, at, sort)
  }

  def findOne(idOrSlug: String): Option[MDocument] = {
    documentModel.findOne(idOrSlug)
  }

  def findOneById(id: SuperId): Option[MDocument] = {
    documentModel.findOneById(id)
  }

  def findFile(idOrSlug: String): Option[File] = {
    (if (SuperId.isValid(idOrSlug)) Some(idOrSlug) else None) orElse
      findIdBySlug(idOrSlug).map(_.value) map
      (id => new File(conf.server.localWebapp + "/document/" + id + ".html"))
  }

  def findIdBySlug(slug: String): Option[SuperId] = documentModel.findIdBySlug(slug)

  def listHtml(limit: Int, at: Option[DateTime], sort: SortAt): HttpResponse = {
    val pager = documentModel.pager(limit, at, sort)
    val out = new ByteArrayOutputStream()
    try {
      helper.makeList(pager, out)
      val entity = HttpEntity(ContentType(MediaTypes.`text/html`, HttpCharsets.`UTF-8`), out.toByteArray)
      HttpResponse(StatusCodes.OK, entity)
    } finally {
      out.close()
    }
  }

  val documentModel = DocumentModel(entities)

}
