package yangbajing.util.data

import yangbajing.common.EnumTrait

/**
 * 数据类型
 */
object DataGenre extends Enumeration with EnumTrait {
  val TEXT = Value(1, "text")
  val INT = Value("int")

  // long, bigint
  val LONG = Value("long")

  // double, float, bigdecimal
  val NUMBER = Value("number")

  val DATETIME = Value("datetime")
  val DATE = Value("date")
  val TIME = Value("time")

  val TEXT_ARRAY = Value("text_array")
  val INT_ARRAY = Value("int_array")
  val LONG_ARRAY = Value("long_array")
  val NUMBER_ARRAY = Value("number_array")
  val DATETIME_ARRAY = Value("datetime_array")
  val DATE_ARRAY = Value("date_array")
  val TIME_ARRAY = Value("time_array")

  val FORMULA = Value("formula")
  val ENUM = Value("enum")
}

/**
 * @param name
 * @param genre
 * @param label
 */
case class DataLabel(name: String,
                     genre: String,
                     label: Option[String] = None) {

  lazy val genreEnum = DataGenre.parse(genre)
}
