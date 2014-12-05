package com.helloscala.platform.common

import com.helloscala.platform.util.Y

trait MyType {
  val value: Int
  val name: String

  val VALUE = value
  val NAME = name
}

trait MyTypes[T <: MyType] {
  def extraction(value: Int): T

  def extraction(name: String): T

  def extractionOpt(any: Any): Option[T] = {
    any match {
      case i: Int => Y.tryOption(extraction(i))
      case s: String => Y.tryOption(extraction(s))
      case _ => None
    }
  }

}
