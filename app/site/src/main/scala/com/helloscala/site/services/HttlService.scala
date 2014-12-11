package com.helloscala.site.services

import java.io.ByteArrayOutputStream
import java.util

import com.helloscala.platform.model.entity.Entities
import com.helloscala.platform.util.{Conf, StatusMsgs}
import com.helloscala.site.beans.{Page500Bean, Page404Bean, IndexBean}
import httl.Engine
import spray.http._
import yangbajing.common.MessageException

class HttlService(conf: Conf, entities: Entities, httlEngine: Engine) {
  def index(bean: IndexBean): HttpResponse = try {
    val params = new util.HashMap[String, AnyRef]()
    params.put("index", bean)
    val data = render("/index.httl", params)

    HttpResponse(StatusCodes.OK, HttpEntity(ContentType(MediaTypes.`text/html`, HttpCharsets.`UTF-8`), data))
  } catch {
    case e: Exception =>
      throw MessageException(StatusMsgs.httlReaderError(e.getLocalizedMessage))
  }

  def page404(bean: Page404Bean): HttpResponse = try {
    val params = new util.HashMap[String, AnyRef]()
    params.put("page404", bean)
    val data = render("/404.httl", params)

    HttpResponse(StatusCodes.OK, HttpEntity(ContentType(MediaTypes.`text/html`, HttpCharsets.`UTF-8`), data))
  } catch {
    case e: Exception =>
      e.printStackTrace()
      throw MessageException(StatusMsgs.DataNotFound)
  }

  def page500(bean: Page500Bean): HttpResponse = try {
    val params = new util.HashMap[String, AnyRef]()
    params.put("page500", bean)
    val data = render("/500.httl", params)
    HttpResponse(StatusCodes.OK, HttpEntity(ContentType(MediaTypes.`text/html`, HttpCharsets.`UTF-8`), data))
  } catch {
    case e: Exception =>
      throw MessageException(StatusMsgs.DataNotFound)
  }

  def render(template: String, parameters: util.HashMap[String, AnyRef]): Array[Byte] = {
    val out = new ByteArrayOutputStream()
    try {
      val tpl = httlEngine.getTemplate(template)
      tpl.render(parameters, out)
      out.toByteArray
    } finally {
      out.close()
    }
  }
}
