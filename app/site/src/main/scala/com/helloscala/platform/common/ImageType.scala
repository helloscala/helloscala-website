package com.helloscala.platform.common

import com.helloscala.platform.util.Y
import spray.http.{MediaType, MediaTypes}

/**
 * 图片类型
 * Created by Yang Jing on 2014-11-21.
 */
sealed abstract class ImageType(val value: Int, val name: String) extends MyType

object ImageTypes extends MyTypes[ImageType] {
  case object Png extends ImageType(1, "png")
  case object Jpeg extends ImageType(2, "jpeg")
  case object Gif extends ImageType(3, "gif")

  override def extraction(value: Int): ImageType = value match {
    case Png.VALUE => Png
    case Jpeg.VALUE => Jpeg
    case Gif.VALUE => Gif
  }

  override def extraction(name: String): ImageType = name match {
    case Png.NAME => Png
    case Jpeg.NAME => Jpeg
    case Gif.NAME => Gif
  }

  def extraction(v: MediaType): ImageType = v match {
    case MediaTypes.`image/png` => Png
    case MediaTypes.`image/jpeg` => Jpeg
    case MediaTypes.`image/gif` => Gif
    case _ => throw new NoSuchElementException("Only allow image/png, jpeg, gif")
  }

  override def extractionOpt(any: Any): Option[ImageType] = {
    any match {
      case v: MediaType => Y.tryOption(extraction(v))
      case _ => super.extractionOpt(any)
    }
  }

}
