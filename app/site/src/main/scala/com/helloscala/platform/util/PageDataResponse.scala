package com.helloscala.platform.util

import yangbajing.common.{TDataMessage, BaseY}

/**
 * @param limit
 * @param newer_time
 * @param older_time
 * @param page 当前页（若设置此值，则newer_time和older_timer无效）
 */
case class Params(limit: Int, newer_time: Option[Long], older_time: Option[Long], page: Option[Int]) {
  override def toString: String = {
    val newerTime = newer_time.map(BaseY.formatDateTimeMillis.print)
    val olderTime = older_time.map(BaseY.formatDateTimeMillis.print)
    s"${getClass.getSimpleName}($limit, $newerTime $newer_time, $olderTime $older_time, $page)"
  }
}

/**
 * 返回分页数据，返回数据降序排列。使用created_at作为排序字段
 * Created by Yang Jing on 2014-11-20.
 */
trait PageDataResponse[Bean] extends TDataMessage {
  val items: List[Bean]

  val count: Long

  val params: Params

  def toHumanFriendlyString(): String = {
    s"items:\n${items.mkString("\n")}\ntotal: $count\nparams: $params"
  }
}


/**
 *
 * @param limit
 * @param newer_id
 * @param older_id
 * @param page 当前页（若设置此值，则newer_id和older_id无效）
 * @tparam ID
 */
case class IdParams[ID](limit: Int, newer_id: Option[ID], older_id: Option[ID], page: Option[Int])

/**
 * 返回分页数据，返回数据降序排列。使用id作为排序字段。
 * Created by Yang Jing on 2014-11-26.
 */
trait PageIdDataResponse[ID <: AnyVal, Bean] extends TDataMessage {
  val items: List[Bean]

  val count: Long

  val params: IdParams[ID]

  def toHumanFriendlyString(): String = {
    s"items:\n${items.mkString("\n")}\ntotal: $count\nparams: $params"
  }
}