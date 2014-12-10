package com.helloscala.site.services

import java.util.Properties
import java.util.concurrent.TimeUnit

import akka.actor.{ActorRefFactory, ActorSystem}
import akka.util.Timeout
import com.helloscala.platform.driver.MyJdbcDriver
import com.helloscala.platform.model.entity.Entities
import com.helloscala.platform.util.{Conf, Tools}
import com.helloscala.site.bootstrap.ServerShutdown
import httl.Engine

class SystemContext(val conf: Conf, actorSystem: ActorSystem)(implicit actorRefFactory: ActorRefFactory) {
  val timeout = Timeout(conf.server.timeout, TimeUnit.MILLISECONDS)
  val entities = new Entities(conf.sql_db, MyJdbcDriver.is)
  val httlEngine = {
    val props = new Properties()
    props.setProperty("loaders", "httl.spi.loaders.FileLoader")
    props.setProperty("template.directory", conf.server.localWebapp)
    Engine.getEngine(props)
  }

  val sessionService = new SessionService(conf, entities)
  val userService = new UserService(conf, entities)
  val imageService = new ImageService(conf, entities)
  val documentService = new DocumentService(conf, entities, httlEngine)

  def scheduler() = actorSystem.scheduler

  def shutdown() = {
    actorSystem.actorSelection(Tools.akkaLocalPath(conf.meta.id, Tools.ServerActorName)) ! ServerShutdown
  }
}

trait SystemContextHelper {
  val systemContext: SystemContext
}
