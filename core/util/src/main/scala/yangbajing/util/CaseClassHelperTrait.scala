package yangbajing.util

import org.json4s._
import org.joda.time.{LocalTime, LocalDate, DateTime}
import yangbajing.common.BaseY

trait CaseClassHelperTrait {
  self: Product with Serializable =>

  protected implicit val json4sDefaultFormats = org.json4s.DefaultFormats ++ org.json4s.ext.JodaTimeSerializers.all

  def toJValue: JValue = Extraction.decompose(this)

  @inline
  def toMap = toJValue.values.asInstanceOf[Map[String, Any]]

  def toStringMap: Map[String, String] =
    toJValue.values.asInstanceOf[Map[String, Any]] map (entry =>
      entry._1 -> (entry._2 match {
        case None =>
          ""

        case Some(x) =>
          x match {
            case v: DateTime => BaseY.formatDateTime.print(v)
            case v: Enumeration#Value => v.toString
            case v: LocalDate => BaseY.formatDate.print(v)
            case v: LocalTime => BaseY.formatTime.print(v)
            case vs: List[_] => vs.map(_.toString).mkString(", ")
            case v => v.toString
          }

        case v: DateTime =>
          BaseY.formatDateTime.print(v)

        case v: Enumeration#Value =>
          v.toString

        case vs: List[_] =>
          vs.map(_.toString).mkString(", ")

        case v: LocalDate =>
          BaseY.formatDate.print(v)

        case v: LocalTime =>
          BaseY.formatTime.print(v)

        case v =>
          v.toString
      }))

}
