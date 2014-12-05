package com.helloscala.platform.model

import java.sql.Timestamp

import com.helloscala.platform.common.{SortAt, SortAts}
import com.helloscala.platform.model.entity.{BaseEntity, Entities}
import org.joda.time.DateTime

import scala.slick.jdbc.StaticQuery

trait BaseModel {
  val entities: Entities

  import entities.driver.simple._

  type ID
  type PK
  type Entity <: BaseEntity[ID]

  protected def offset(limit: Int, page: Int) = if (page > 0) limit * (page - 1) else 0

  protected def pagerSort[T](col: Column[T], sortAt: SortAt) = {
    sortAt match {
      case SortAts.Asc => col.asc
      case SortAts.Desc => col.desc
    }
  }

  protected def count(t: Table[_])(implicit ss: Session): Long = {
    StaticQuery.queryNA[Long]("select count(1) from " + t.tableName).first
  }

  protected def minMaxCreatedAt(t: Table[_])(implicit ss: Session): (Option[Timestamp], Option[Timestamp]) = {
    StaticQuery.queryNA[(Option[Timestamp], Option[Timestamp])]("select min(created_at), max(created_at) from " + t.tableName).first
  }

  protected def minMaxIdLong(t: Table[_])(implicit ss: Session): (Option[Long], Option[Long]) = {
    StaticQuery.queryNA[(Option[Long], Option[Long])]("select min(id), max(id) from " + t.tableName).first
  }

  protected def minMaxIdInt(t: Table[_])(implicit ss: Session): (Option[Int], Option[Int]) = {
    StaticQuery.queryNA[(Option[Int], Option[Int])]("select min(id), max(id) from " + t.tableName).first
  }

  protected def pagerFilter(t: entities.MyTable[Int, _], at: Int, sortAt: SortAt): Column[Boolean] = {
    sortAt match {
      case SortAts.Asc => t.id > at
      case SortAts.Desc => t.id < at
    }
  }

  protected def pagerFilter(t: entities.MyTable[Long, _], at: Long, sortAt: SortAt): Column[Boolean] = {
    sortAt match {
      case SortAts.Asc => t.id > at
      case SortAts.Desc => t.id < at
    }
  }

  protected def pagerFilter(t: entities.MyTable[_, _], at: DateTime, sortAt: SortAt): Column[Boolean] = {
    sortAt match {
      case SortAts.Asc => t.created_at > at
      case SortAts.Desc => t.created_at < at
    }
  }
}
