package com.helloscala.platform.model.entity

import com.helloscala.platform.common.ImageType
import org.joda.time.DateTime

/**
 * 图片资源
 * Created by Yang Jing on 2014-11-21.
 */
case class MImage(// sha1
                  id: String,

                  // 图片类型
                  media_type: ImageType,

                  // 图片长度，字节
                  length: Int,

                  created_at: DateTime = DateTime.now()) extends BaseEntity[String] {
  def toUri = s"/images/${id.take(2)}/$id.${media_type.NAME}"
}
