package com.helloscala.platform.util

import java.util.concurrent.TimeUnit

import com.typesafe.config.ConfigFactory

trait ConfHelper {
  def getConfig(confName: String) = {
    val cn = ConfigFactory.load().getConfig(confName)

    val meta = {
      val c = cn.getConfig("meta")
      MetaConf(c.getString("id"), c.getString("description"), c.getInt("default-group"), c.getInt("default-limit"))
    }

    val server = {
      val c = cn.getConfig("server")
      ServerConf(c.getString("interface"), c.getInt("port"), c.getString("host"), c.getString("local-base"),
        c.getString("context-path"), c.getDuration("session-timeout", TimeUnit.MILLISECONDS),
        c.getDuration("timeout", TimeUnit.MILLISECONDS))
    }

    val sqlDb = {
      val c = cn.getConfig("sql-db")
      SqlDbConf(c.getString("url"), c.getString("username"),
        c.getString("password"))
    }

    Conf(meta, server, sqlDb)
  }
}

case class Conf(meta: MetaConf, server: ServerConf, sql_db: SqlDbConf)

/**
 * @param id
 * @param name
 * @param default_group 默认用户组ID
 * @param default_limit 默认从数据库读取行数
 */
case class MetaConf(id: String, name: String,
                    default_group: Int, default_limit: Int)

case class ServerConf(interface: String, port: Int, host: String, local_base: String, context_path: String,
                      session_timeout: Long, timeout: Long) {
  val localWebapp = local_base + "/webapp"
  val hostAndContext = host + context_path
}

case class DbConf(server: String, port: Int, dbname: String, username: String, password: String)

case class SqlDbConf(url: String, username: String, password: String)
