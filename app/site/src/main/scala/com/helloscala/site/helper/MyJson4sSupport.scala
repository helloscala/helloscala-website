package com.helloscala.site.helper

import java.lang.reflect.InvocationTargetException

import com.helloscala.platform.model.entity.MUser
import com.helloscala.platform.util.Y
import com.helloscala.platform.util.Y.json4sDefaultFormats
import com.helloscala.site.services.{ReqAuth, ReqUser}
import com.typesafe.scalalogging.LazyLogging
import org.json4s.JsonAST.{JInt, JObject, JString}
import org.json4s.{JValue, MappingException}
import spray.http.{ContentTypes, HttpCharsets, HttpEntity, MediaTypes}
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.Unmarshaller
import yangbajing.common.{TDataMessage, TStatusMessage, TRespMessage}

trait Json4sUnmarshaller {
  protected def json4sUnmarshaller[T: Manifest] =
    Unmarshaller[T](MediaTypes.`application/json`) {
      case x: HttpEntity.NonEmpty ⇒
        val s = x.asString(defaultCharset = HttpCharsets.`UTF-8`)
        try {
          Y.json4sToJValue(s).extract[T]
        } catch {
          case MappingException("unknown error", ite: InvocationTargetException) =>
            ite.printStackTrace()
            throw ite.getCause
          case e: Exception =>
            println(s)
            e.printStackTrace()
            throw e
        }
    }

  implicit def __reqAuthUnmarshaller: Unmarshaller[ReqAuth] = json4sUnmarshaller[ReqAuth]

  implicit def __reqUserUnmarshaller: Unmarshaller[ReqUser] = json4sUnmarshaller[ReqUser]

  implicit def __muserUnmarshaller: Unmarshaller[MUser] = json4sUnmarshaller[MUser]
}

object Json4sUnmarshaller extends Json4sUnmarshaller

trait Json4sMarshaller {
  //  implicit val __jvalueMarshaller =
  //    Marshaller.delegate[JValue, String](ContentTypes.`application/json`) { jvalue =>
  //      Json4sMarshaller.toString(jvalue)
  //    }

  implicit val __errorMessageMarshaller =
    Marshaller.delegate[TStatusMessage, String](ContentTypes.`application/json`) { msg =>
      val jv = Y.json4sDecompose(msg)
      Json4sMarshaller.toString(JObject("status" -> jv))
    }

  implicit val __dataMessageMarshaller =
    Marshaller.delegate[TDataMessage, String](ContentTypes.`application/json`) { msg =>
      val jv = Y.json4sDecompose(msg)
      Json4sMarshaller.toString(JObject("status" -> JObject("code" -> JInt(0), "msg" -> JString("")), "data" -> jv))
    }

  implicit val __respMessageMarshaller =
    Marshaller.delegate[TRespMessage, String](ContentTypes.`application/json`) { v =>
      val jv = Y.json4sDecompose(v)
      Json4sMarshaller.toString(jv)
    }

}

object Json4sMarshaller extends Json4sMarshaller with LazyLogging {
  private def toString(jv: JValue): String = {
    val result = Y.json4sToString(jv)
    logger.debug(result)
    result
  }
}

trait MyJson4sSupport extends Json4sUnmarshaller with Json4sMarshaller

object MyJson4sSupport extends MyJson4sSupport
