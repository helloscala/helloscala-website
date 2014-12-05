package com.helloscala.platform.driver

import com.github.tminglei.slickpg.{PgArraySupport, PgDateSupportJoda, PgHStoreSupport, PgJson4sSupport}
import com.helloscala.platform.common._
import org.json4s.JValue
import org.json4s.jackson.JsonMethods

import scala.slick.driver.{JdbcDriver, PostgresDriver}

/**
 * Slick JDBC Driver
 * Created by Yang Jing on 2014-10-26.
 */
object MyJdbcDriver {
  type DriverType = is.type

  val is = MyPostgres

  def apply() = is

  final val SuperIdType = "char(24)"
}

object MyPostgres
  extends PostgresDriver
  with PgJson4sSupport
  with PgDateSupportJoda
  with PgHStoreSupport
  with PgArraySupport
  //  with PgRangeSupport
  //  with PgPostGISSupport
  //  with PgSearchSupport
  with MyEntityDriver {

  type DOCType = JValue
  val jsonMethods = JsonMethods
  override lazy val Implicit = new ImplicitsPlus {}
  override val simple = new SimpleQLPlus {}

  trait ImplicitsPlus
    extends Implicits
    with ArrayImplicits
    with DateTimeImplicits
    with HStoreImplicits
    with JsonImplicits
    with MyEntityImplicits

  //    with RangeImplicits
  //    with SearchImplicits
  //    with PostGISImplicits

  trait SimpleQLPlus extends SimpleQL with ImplicitsPlus

  //    with SearchAssistants
  //    with PostGISAssistants

  final val DB_TYPE_TEXT = "text"
  final val DRIVER_CLASS = "org.postgresql.Driver"
}

trait MyEntityDriver {
  this: JdbcDriver =>

  trait MyEntityImplicits {
    this: Implicits =>
    implicit val __accountColumnType = MappedColumnType.base[AccountType, Int](_.value, AccountTypes.extraction)
    implicit val __sexColumnType = MappedColumnType.base[SexType, Int](_.value, SexTypes.extraction)
    implicit val __pagerAtColumnType = MappedColumnType.base[SortAt, Int](_.value, SortAts.extraction)
    implicit val __imageColumnType = MappedColumnType.base[ImageType, Int](_.value, ImageTypes.extraction)
  }

}
