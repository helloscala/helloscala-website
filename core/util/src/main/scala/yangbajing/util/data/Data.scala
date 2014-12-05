package yangbajing.util.data

import scala.collection.mutable
import org.joda.time.DateTime

import yangbajing.common.A

case class AnyData(
  name: String = "default",
  data: mutable.Map[String, Any] = mutable.Map[String, Any](),
  list: mutable.Map[String, DataList[Any]] = mutable.Map[String, DataList[Any]](),
  metadata: Option[MutableData[Any]] = None) {

  def dataString(key: String): Option[String] =
    A orString data.get(key)

  def getDataString(key: String, deft: => String): String =
    dataString(key) getOrElse deft
}

case class MutableData[V](
  name: String = "default",
  data: mutable.Map[String, V] = mutable.Map[String, V](),
  list: mutable.Map[String, DataList[V]] = mutable.Map[String, DataList[V]](),
  metadata: Option[MutableData[V]] = None)

case class MutableDataStr(
  name: String = "default",
  data: mutable.Map[String, String] = mutable.Map[String, String](),
  list: mutable.Map[String, DataListStr] = mutable.Map[String, DataListStr](),
  createdAt: DateTime = new DateTime,
  metadata: Option[MutableDataStr] = None)

case class Data[V](
  name: String = "default",
  data: Map[String, V] = Map[String, V](),
  list: Map[String, DataList[V]] = Map[String, DataList[V]](),
  metadata: Option[Data[V]] = None)

trait TMutableData[V, Self] {
  def self: Self

  def metadata: MutableData[V]

  def putMetadata(key: String, value: V): Option[V] =
    metadata.data.put(key, value)

  def getMetadata(key: String): Option[V] =
    metadata.data.get(key)

  def getOrElseMetadata[B >: V](key: String, deft: B) =
    metadata.data.getOrElse(key, deft)
}

trait TData[K, V] {
  def metadata: Data[V]
}
