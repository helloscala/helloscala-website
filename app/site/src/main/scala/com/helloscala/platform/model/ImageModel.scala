package com.helloscala.platform.model

import com.helloscala.platform.common.ImageType
import com.helloscala.platform.model.entity.{Entities, MImage}
import com.helloscala.platform.util.Y

/**
 * 图片
 * Created by Yang Jing on 2014-11-21.
 */
object ImageModel {
  def apply(entities: Entities) = new ImageModel(entities)
}

class ImageModel private(val entities: Entities) extends BaseModel {

  type ID = String
  type PK = String
  type Entity = MImage

  import entities._
  import entities.driver.simple._

  /**
   * @param basePath 要保存的基础路径
   * @param imageType 图片类型
   * @param bytes 图片数据
   * @return Either[已存在图片, 新保存图片]
   */
  def trySave(basePath: String, imageType: ImageType, bytes: Array[Byte]): Either[MImage, MImage] = db withTransaction { implicit ss =>
    val sha1 = Y.ySha1(bytes)
    tImage.filter(_.id === sha1).firstOption match {
      case Some(bean) =>
        Left(bean)

      case None =>
        val bean = MImage(sha1, imageType, bytes.length)
        tImage += bean
        val localFile = basePath + bean.toUri
        Y.saveWithFile(localFile)(_.write(bytes))

        Right(bean)
    }
  }

  def insert(entity: MImage): MImage = db withTransaction { implicit ss =>
    tImage += entity
    entity
  }

  def findOneById(id: PK): Option[MImage] = db withSession { implicit ss =>
    tImage.filter(_.id === id).firstOption

  }

}
