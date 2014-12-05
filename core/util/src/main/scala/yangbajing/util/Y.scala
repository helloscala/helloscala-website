package yangbajing.util

import org.bson.types.ObjectId
import org.json4s.JsonAST._
import org.json4s.jackson.{JsonMethods, Serialization}
import org.json4s.{Extraction, Formats, JValue}
import yangbajing.common.BaseY

trait Y extends BaseY {
  @inline
  def isBlank(v: ObjectId): Boolean = v eq null

  @inline
  def nonBlank(v: ObjectId): Boolean = v ne null

  def json4sToJValueOption(v: String)(implicit formats: Formats): Option[JValue] =
    tryOption(json4sToJValue(v))

  def json4sToJValue(s: String)(implicit formats: Formats): JValue =
    JsonMethods.parse(s)

  def json4sCompact(v: JValue)(implicit formats: Formats): String =
    JsonMethods.compact(v)

  def json4sFieldString(v: JValue): String = v.asInstanceOf[JString].values

  def json4sFieldInt(v: JValue): Int = v.asInstanceOf[JInt].values.toInt

  def json4sFieldBigInt(v: JValue): BigInt = v.asInstanceOf[JInt].values

  def json4sFieldLong(v: JValue): Long = v.asInstanceOf[JInt].values.toLong

  def json4sFieldDouble(v: JValue): Double = v.asInstanceOf[JDouble].values

  def json4sFieldDecimal(v: JValue): BigDecimal = v.asInstanceOf[JDecimal].values

  def json4sFieldBoolean(v: JValue): Boolean = v.asInstanceOf[JBool].values

  def json4sToString(v: AnyRef)(implicit formats: Formats): String =
    Serialization.write(v)

  def json4sToStringOption(v: AnyRef)(implicit formats: Formats): Option[String] =
    tryOption(Serialization.write(v))

  def json4sToStringPretty(v: AnyRef)(implicit formats: Formats): String =
    Serialization.writePretty(v)

  def json4sToStringPrettyOption(v: AnyRef)(implicit formats: Formats): Option[String] =
    tryOption(json4sToStringPretty(v))

  def json4sExtract[A](s: String)(implicit formats: Formats, mf: scala.reflect.Manifest[A]) =
    JsonMethods.parse(s).extract[A]

  def json4sDecompose(v: Any)(implicit formats: Formats): JValue =
    Extraction.decompose(v)
}
