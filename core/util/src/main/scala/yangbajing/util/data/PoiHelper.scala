package yangbajing.util.data

import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook

object PoiHelper {

  def xls(headers: Seq[DataLabel], tables: Seq[Map[String, Any]], sheetName: String = "Sheet 1"): XSSFWorkbook = {
    val table = new PoiHelper(headers, tables, new XSSFWorkbook(), Seq(sheetName))
    table.execute.wb
  }

  def xlsx(headers: Seq[DataLabel], tables: Seq[Map[String, Any]], sheetName: String = "Sheet 1"): HSSFWorkbook = {
    val table = new PoiHelper(headers, tables, new HSSFWorkbook(), Seq(sheetName))
    table.execute.wb
  }

  def xlsSheet(wb: XSSFWorkbook, headers: Seq[DataLabel], tables: Seq[Map[String, Any]], sheetName: String = "Sheet 1") = {
    new PoiSheet(wb, headers, tables, sheetName).execute
  }

  def xlsxSheet(wb: HSSFWorkbook, headers: Seq[DataLabel], tables: Seq[Map[String, Any]], sheetName: String = "Sheet 1") =
    new PoiSheet(wb, headers, tables, sheetName).execute

  def toXls(headers: Seq[(String, String)], tables: Seq[Map[String, String]], sheetName: String = "Sheet 1"): XSSFWorkbook = {
    val table = new PoiTable(headers, tables, new XSSFWorkbook(), Seq(sheetName))
    table.execute.wb
  }

  def toXlsx(headers: Seq[(String, String)], tables: Seq[Map[String, String]], sheetName: String = "Sheet 1"): HSSFWorkbook = {
    val table = new PoiTable(headers, tables, new HSSFWorkbook(), Seq(sheetName))
    table.execute.wb
  }

  class PoiTable[T <: Workbook](
                                 header: Seq[(String, String)],
                                 tables: Seq[Map[String, String]],
                                 val wb: T,
                                 sheetNames: Seq[String]) extends AbstractPoi {

    def execute = {
      val sheet = wb.createSheet(sheetNames.head)
      val rowTitle = sheet.createRow(0)

      for (((key, value), col) <- header.zipWithIndex) {
        rowTitle.createCell(col).setCellValue(value)
      }

      for ((map, rowIdx) <- tables.zipWithIndex) {
        val row = sheet.createRow(rowIdx + 1)
        for (((key, _), col) <- header.zipWithIndex)
          createCellValue(row, col, DataGenre.TEXT, map.getOrElse(key, ""))
      }

      this
    }

  }

}


private class PoiHelper[T <: Workbook](val header: Seq[DataLabel],
                                       val tables: Seq[Map[String, Any]],
                                       val wb: T,
                                       val sheetNames: Seq[String]) extends AbstractPoi {

  def execute = {
    val sheet = wb.createSheet(sheetNames.head)
    val rowTitle = sheet.createRow(0)

    for ((data, col) <- header.zipWithIndex) {
      rowTitle.createCell(col).setCellValue(data.label getOrElse data.name)
    }

    for ((map, idx) <- tables.zipWithIndex) {
      val row = sheet.createRow(idx + 1)

      for ((data, col) <- header.zipWithIndex)
        map.get(data.name) match {
          case Some(value) =>
            createCellValue(row, col, data.genreEnum, value)
          case None =>
            createCell(row, col)
        }
    }

    this
  }

}


