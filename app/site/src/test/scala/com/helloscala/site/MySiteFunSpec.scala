package com.helloscala.site

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.util.Timeout
import com.helloscala.platform.MyFunSpec
import com.helloscala.platform.util.Y
import org.json4s.Formats
import org.scalatest.Suite

import scala.concurrent.{Await, Awaitable}

trait MySiteFunSpec extends MyFunSpec {
  this: Suite =>

  implicit val system = ActorSystem()
  implicit val timeout = Timeout(10, TimeUnit.SECONDS)

  override protected def afterAll(): Unit = {
    system.shutdown()
    super.afterAll()
  }

  def dump[T <: AnyRef](f: Awaitable[T])(implicit timeout: Timeout, format: Formats): Unit = {
    val result = Await.result(f, timeout.duration)
    println(Y.json4sToStringPretty(result))
  }
}
