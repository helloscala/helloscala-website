package yangbajing.util.data

import yangbajing.util.ExportLike
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook

trait ExportModel {
  def header: Seq[DataLabel]

  def toXls[T <: ExportLike](entity: T): XSSFWorkbook =
    PoiHelper.xls(header, Seq(entity.toMap), "1")

  def toXlsx[T <: ExportLike](entity: T): HSSFWorkbook =
    PoiHelper.xlsx(header, Seq(entity.toMap), "1")

  def toXls[T <: ExportLike](entity: T, sheetName: String): XSSFWorkbook =
    PoiHelper.xls(header, Seq(entity.toMap), sheetName)

  def toXlsx[T <: ExportLike](entity: T, sheetName: String): HSSFWorkbook =
    PoiHelper.xlsx(header, Seq(entity.toMap), sheetName)
}
