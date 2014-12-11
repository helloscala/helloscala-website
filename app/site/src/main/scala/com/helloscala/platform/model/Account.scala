package com.helloscala.platform.model

case class Account(id: Long, email: String, username: Option[String], nick: Option[String], roles: List[Int],
                   attrs: Map[String, String])
