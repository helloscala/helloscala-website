package com.helloscala.site.services

import java.io.File

import com.helloscala.platform.common.SuperId
import com.helloscala.platform.model.DocumentModel
import com.helloscala.platform.model.entity.{MDocument, Entities}
import com.helloscala.platform.util.Conf

/**
 * 文档服务
 * Created by Yang Jing on 2014-11-25.
 */
class DocumentService(conf: Conf, entities: Entities) {
  val documentModel = DocumentModel(entities)

  def findOne(idOrSlug: String): Option[MDocument] = {
    documentModel.findOne(idOrSlug)
  }

  def findFile(idOrSlug: String): Option[File] = {
    (if (SuperId.isValid(idOrSlug)) Some(idOrSlug) else None) orElse
      findIdBySlug(idOrSlug).map(_.value) map
      (id => new File(conf.server.localWebapp + "/document/" + id + ".html"))
  }

  def findIdBySlug(slug: String): Option[SuperId] = documentModel.findIdBySlug(slug)

}
