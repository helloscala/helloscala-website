package yangbajing.util.data

import java.util.Date

import scala.collection.mutable

import org.joda.time.DateTime

import org.bson.types.ObjectId

import yangbajing.common.{A, KV, NVString}

case class Line[ID, V](id: ID, var line: List[KV[String, V]] = Nil)

case class LineStr(id: String, var line: List[NVString] = Nil)

case class AnyLine[ID](id: ID,
                       var line: List[KV[String, Any]] = Nil) {

  def getInt(key: String): Int =
    A.orInt(line.find(_.k == key)).get

  def orInt(key: String, deft: => Int): Int =
    A orInt(line.find(_.k == key), deft)

  def orLong(key: String, deft: => Long): Long =
    A orLong(line.find(_.k == key), deft)

  def orDateTime(key: String, deft: => DateTime): DateTime =
    A orDateTime(line.find(_.k == key), deft)

  def orString(key: String, deft: => String): String =
    A orString(line.find(_.k == key), deft)

  def orBoolean(key: String, deft: => Boolean): Boolean =
    A orBoolean(line.find(_.k == key), deft)

  def orObjectId(key: String, deft: => ObjectId): ObjectId =
    A orObjectId(line.find(_.k == key), deft)

  def orDouble(key: String, deft: => Double): Double =
    A orDouble(line.find(_.k == key), deft)

  def orDate(key: String, deft: => Date): Date =
    A orDate(line.find(_.k == key), deft)
}

case class AnyTable(
                     var name: String = "default",
                     main: mutable.Map[String, Any] = mutable.Map(),
                     body: mutable.ArrayBuffer[AnyLine[String]] = mutable.ArrayBuffer.empty[AnyLine[String]],
                     var table: Option[AnyTable] = None)

case class MutableTable[V](
                            var name: String = "default",
                            main: mutable.Map[String, V] = mutable.Map(),
                            body: mutable.ArrayBuffer[Line[String, V]] = mutable.ArrayBuffer.empty[Line[String, V]],
                            var table: Option[MutableTable[V]] = None)

case class MutableTableStr(
                            var name: String = "default",
                            main: mutable.Map[String, String] = mutable.Map(),
                            body: mutable.ArrayBuffer[LineStr] = mutable.ArrayBuffer.empty[LineStr],
                            var table: Option[MutableTableStr] = None)

case class Table[V](
                     name: String = "default",
                     main: Map[String, V] = Map[String, V](),
                     body: List[Line[String, V]] = Nil,
                     table: Option[Table[V]] = None)

  