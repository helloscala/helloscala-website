package com.helloscala.platform.common

sealed abstract class SexType(val value: Int, val name: String) extends MyType

object SexTypes extends MyTypes[SexType] {

  case object Unknown extends SexType(0, "未知")

  case object Male extends SexType(1, "男士")

  case object Female extends SexType(2, "女士")

  override def extraction(value: Int): SexType = value match {
    case Unknown.VALUE => Unknown
    case Male.VALUE => Male
    case Female.VALUE => Female
  }

  override def extraction(name: String): SexType = name match {
    case Unknown.NAME => Unknown
    case Male.NAME => Male
    case Female.NAME => Female
  }
}
