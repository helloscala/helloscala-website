package com.helloscala.platform.model

case class Account(id: Long, username: String, email: Option[String], nick: Option[String], roles: List[Int],
                   attrs: Map[String, String])
