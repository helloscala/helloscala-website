package com.helloscala.site.helper

import java.lang.reflect.InvocationTargetException

import akka.actor.ActorRefFactory
import com.helloscala.platform.util.Y.json4sDefaultFormats
import com.helloscala.platform.util.{Conf, StatusMsg, Y}
import com.helloscala.site.services.ReqAuth
import com.helloscala.site.services.session.SessionAccount
import org.json4s.{MappingException, JValue}
import spray.client.pipelining._
import spray.http._
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.Unmarshaller
import yangbajing.common.TRespMessage

import scala.concurrent.Future

class HttpClient(conf: Conf)(implicit actorRefFactory: ActorRefFactory) extends Json4sUnmarshaller {

  import actorRefFactory.dispatcher

  implicit val unmarshallerSessionAccount = json4sUnmarshaller[ResponseSessionAccount]

  implicit val unmarshallerJValue =
    Unmarshaller[JValue](MediaTypes.`application/json`) {
      case x: HttpEntity.NonEmpty ⇒
        val s = x.asString(defaultCharset = HttpCharsets.`UTF-8`)
        try {
          Y.json4sToJValue(s)
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


  implicit val marshallerReqAuth =
    Marshaller.delegate[ReqAuth, String](ContentTypes.`application/json`) { msg =>
      val jv = Y.json4sDecompose(msg)
      val v = Y.json4sToStringPretty(jv)
      println(v)
      v
    }

  val pipelineJValue = sendReceive ~> unmarshal[JValue]

  def registerUser(email: String, md5Pass: String): Future[ResponseSessionAccount] = {
    (sendReceive ~> unmarshal[ResponseSessionAccount]) apply
      Post("http://localhost:29090/api/session/register", ReqAuth(email, md5Pass))
  }

  def sessionQuery(token: String): Future[JValue] = {
    pipelineJValue(Get(conf.server.hostAndContext + "/api/session/query/" + token))
  }
}

case class ResponseSessionAccount(status: StatusMsg, data: SessionAccount) extends TRespMessage