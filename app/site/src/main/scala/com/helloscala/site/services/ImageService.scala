package com.helloscala.site.services

import com.helloscala.platform.common.ImageTypes
import com.helloscala.platform.model.ImageModel
import com.helloscala.platform.model.entity.Entities
import com.helloscala.platform.util.{Conf, StatusMsgs, Y}
import spray.http.HttpEntity.NonEmpty
import spray.http.{BodyPart, MultipartFormData}
import yangbajing.common.{MessageException, TDataMessage, TStatusMessage}

import scala.concurrent.{ExecutionContext, Future}

/**
 * 图片服务
 * Created by Yang Jing on 2014-11-21.
 */
class ImageService(conf: Conf, entities: Entities) {
  def saveImage(data: MultipartFormData)(implicit ec: ExecutionContext): Future[SaveImageResponse] = {
    if (data.fields.isEmpty) {
      Future.failed(MessageException(StatusMsgs.UploadDataEmpty))
    } else {
      Future {
        val imageModel = ImageModel(entities)
        val items = data.fields.map(trySaveImage(imageModel, _))
        SaveImageResponse(items)
      }
    }
  }

  private def trySaveImage(imageModel: ImageModel, bodyPart: BodyPart): SaveImageItem = {
    try {
      bodyPart.entity match {
        case entity: NonEmpty =>
          Y.tryOption(ImageTypes.extraction(entity.contentType.mediaType)) match {
            case None =>
              SaveImageItem(StatusMsgs.UploadImageFormatInvalid)

            case Some(imageType) =>
              val bean =
                imageModel.trySave(conf.server.localWebapp, imageType, entity.data.toByteArray) match {
                  case Right(v) => v
                  case Left(v) => v
                }
              val url = /*conf.server.hostAndContext +*/ bean.toUri
              SaveImageItem(StatusMsgs.Ok, url)
          }

        case _ =>
          SaveImageItem(StatusMsgs.InvalidUploadRequest)
      }
    } catch {
      case e: MessageException => SaveImageItem(e.msg)
      case e: Exception => SaveImageItem(StatusMsgs.error(e.getLocalizedMessage))
    }
  }

}

case class SaveImageItem(code: Int, msg: String, url: Option[String]) extends TStatusMessage

object SaveImageItem {
  def apply(msg: TStatusMessage): SaveImageItem = SaveImageItem(msg.code, msg.msg, None)

  def apply(msg: TStatusMessage, url: String): SaveImageItem = SaveImageItem(msg.code, msg.msg, Some(url))
}

case class SaveImageResponse(items: Seq[SaveImageItem]) extends TDataMessage
