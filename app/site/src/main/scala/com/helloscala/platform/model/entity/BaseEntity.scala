package com.helloscala.platform.model.entity

import org.joda.time.DateTime
import yangbajing.common.TDataMessage

trait BaseEntity[ID] extends TDataMessage {
  def id: ID

  def created_at: DateTime
}
