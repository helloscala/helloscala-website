package com.helloscala.platform.common

/**
 * Pager分页模式
 * Created by Yang Jing on 2014-11-20.
 */
sealed abstract class SortAt(val value: Int, val name: String) extends MyType

object SortAts extends MyTypes[SortAt] {

  // 升序 newer
  case object Asc extends SortAt(1, "asc")

  // 降序 older
  case object Desc extends SortAt(2, "desc")

  override def extraction(v: Int) = v match {
    case Asc.VALUE => Asc
    case Desc.VALUE => Desc
  }

  override def extraction(v: String) = v match {
    case Asc.NAME => Asc
    case Desc.NAME => Desc
  }

}