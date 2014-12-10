package com.helloscala.site.helper

import java.io.{File, FileOutputStream, OutputStream}
import java.util

import com.helloscala.platform.model.{DocumentPagerResponse, UserModel}
import com.helloscala.platform.model.entity.{Entities, MDocument}
import com.helloscala.platform.util.{Y, Conf}
import com.helloscala.site.beans.{DocumentPagerBean, DocumentBean}
import httl.Engine
import org.clapper.markwrap.{MarkWrap, MarkupType}

import scala.collection.JavaConverters._
import scala.io.Source

/**
 * 文档工具类
 * Created by Yang Jing on 2014-11-25.
 */
class DocumentHelper(conf: Conf, entities: Entities, httlEngine: Engine) {

  def makeList(pager: DocumentPagerResponse, out: OutputStream): Unit = {
    val parameters = new util.HashMap[String, AnyRef]()

    parameters.put("pager", DocumentPagerBean(pager.items.asJava, pager.count, pager.params))
    //    val source = Source.fromFile(conf.server.localWebapp + "/templates/document.list.httl", "UTF-8").getLines().mkString("\n")
    //    val template = httlEngine.parseTemplate(source)
    val template = httlEngine.getTemplate("/templates/document.list.httl")
    template.render(parameters, out)
  }

  def makeDetail(document: MDocument): Unit = {
    val dir = conf.server.localWebapp + "/document/" + document.created_at.getYear + "/" +
      "%02d".format(document.created_at.getMonthOfYear)
    Y.mkDir(dir)
    val out = new FileOutputStream(new File(dir, document.id.get.value + ".html"))
    try {
      makeDetail(document, out)
    } finally {
      out.close()
    }
  }

  def makeDetail(doc: MDocument, out: OutputStream): Unit = {
    val parameters = new util.HashMap[String, AnyRef]()

    parameters.put("document", documentBean(doc))
    val source = Source.fromFile(conf.server.localWebapp + "/templates/document.detail.httl", "UTF-8").getLines().mkString("\n")
    val template = httlEngine.parseTemplate(source)
    template.render(parameters, out)
  }

  def documentBean(doc: MDocument): DocumentBean = {
    val content = MarkWrap.parserFor(MarkupType.Markdown).parseToHTML(Source.fromString(doc.content))

    val user = UserModel(entities).findOneById(doc.author).get

    DocumentBean(doc.id.get.value, doc.title, doc.author, user.nick.getOrElse(user.username), content,
      doc.description.getOrElse(""), doc.slug.getOrElse(""), doc.allow_anonymous, doc.tags.asJava, doc.attrs.asJava,
      doc.created_at.toDate)
  }
}
