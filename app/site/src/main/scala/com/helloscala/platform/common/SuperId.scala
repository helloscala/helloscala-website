package com.helloscala.platform.common

import org.bson.types.ObjectId

import scala.slick.lifted.MappedTo

/**
 * 全局唯一ID
 * Created by Yang Jing on 2014-11-04.
 */
case class SuperId(value: String = ObjectId.get().toString) extends MappedTo[String] {
  lazy val toObjectId = new ObjectId(value)

  override def toString = value
}

object SuperId {
  final val sqlDbType = "char(24)"

  def isValid(s: String) = ObjectId.isValid(s)
}
