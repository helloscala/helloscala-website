package com.helloscala.site.helper

import java.io.{FileOutputStream, OutputStream}
import java.util

import com.helloscala.platform.model.entity.MDocument
import com.helloscala.platform.util.Conf
import com.helloscala.site.beans.DocumentBean
import httl.Engine
import org.clapper.markwrap.{MarkWrap, MarkupType}

import scala.collection.JavaConverters._
import scala.io.Source

/**
 * 文档工具类
 * Created by Yang Jing on 2014-11-25.
 */
class DocumentHelper(conf: Conf, httlEngine: Engine) {


  def makeHtml(document: MDocument): Unit = {
    val out = new FileOutputStream(conf.server.localWebapp + "/document/" + document.id.get.value + ".html")
    makeHtml(document, out)
    out.close()
  }

  def makeHtml(doc: MDocument, out: OutputStream): Unit = {
    val parameters = new util.HashMap[String, AnyRef]()

    parameters.put("document", documentBean(doc))
    val source = Source.fromFile(conf.server.localWebapp + "/templates/document.html", "UTF-8").getLines().mkString("\n")
    val template = httlEngine.parseTemplate(source)
    template.render(parameters, out)
  }

  def documentBean(doc: MDocument): DocumentBean = {
    val content = MarkWrap.parserFor(MarkupType.Markdown).parseToHTML(Source.fromString(doc.content))


    DocumentBean(doc.id.get.value, doc.title, doc.author, content, doc.description.getOrElse(""),
      doc.slug.getOrElse(""), doc.allow_anonymous, doc.tags.asJava, doc.attrs.asJava, doc.created_at.toDate)
  }
}
