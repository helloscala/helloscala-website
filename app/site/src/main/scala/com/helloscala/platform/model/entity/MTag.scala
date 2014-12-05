package com.helloscala.platform.model.entity

import org.joda.time.DateTime

/**
 * 标签
 * Created by Yang Jing on 2014-11-24.
 */
case class MTag(id: Option[Long],
                content: String,
                created_at: DateTime = DateTime.now()) extends BaseEntity[Option[Long]]
