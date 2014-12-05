package yangbajing.util.data

import yangbajing.common.EnumTrait

object PoiTypes {

  object TestCode extends Enumeration with EnumTrait {
    type TestCode = Value
    val Xls = Value(1, "xls")
    val Xlsx = Value(2, "xlsx")
  }

  object PoiCode extends Enumeration with EnumTrait {
    type PoiCode = Value
    val Xls = Value(1, "xls")
    val Xlsx = Value(2, "xlsx")
  }

}
