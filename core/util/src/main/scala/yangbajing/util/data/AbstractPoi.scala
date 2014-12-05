package yangbajing.util.data

import java.util.Date

import org.apache.poi.ss.usermodel._

import yangbajing.common.A

trait AbstractPoi {
  def wb: Workbook

  protected val creationHelper = wb.getCreationHelper

  def createCell(row: Row, column: Int): Cell =
    row.createCell(column)

  def createCellFormula(row: Row, column: Int, formula: String): Cell = {
    val cell = row.createCell(column)
    SetFormula(cell, formula)
    cell
  }


  def createCellValue(row: Row, column: Int, field: DataGenre.Value, value: Any): Cell = {
    val cell = row.createCell(column)

    field match {
      case DataGenre.DATE =>
        A.orLocalDate(value) foreach (date =>
          setValueDate(cell, DataGenre.DATE, date.toDate))

      case DataGenre.DATETIME =>
        A.orDateTime(value) foreach (date =>
          setValueDate(cell, DataGenre.DATETIME, date.toDate))

      case DataGenre.TIME =>
        A.orLocalTime(value) foreach (time =>
          setValueDate(cell, DataGenre.TIME, time.toDateTimeToday.toDate))

      case DataGenre.LONG =>
        A.orLong(value) foreach (setValueLong(cell, _))

      case DataGenre.INT =>
        A.orInt(value) foreach (i => setValueLong(cell, i.toLong))

      case DataGenre.NUMBER =>
        A.orDouble(value).map(setValueNumber(cell, _)) orElse
          A.orBigDecimal(value).map(d => setValueNumber(cell, d.toDouble)) orElse
          A.orFloat(value).map(d => setValueNumber(cell, d.toDouble))

      case DataGenre.TEXT =>
        setValueText(cell, value.asInstanceOf[String])

      case DataGenre.ENUM => // 直接保存enum的字符串表示显示
        setValueText(cell, value.toString)

      case _ => // 不支持格式
        setValueText(cell, value.toString)
    }

    cell
  }

  protected def setValueDate(cell: Cell, format: DataGenre.Value, value: Date) {
    val style =
      format match {
        case DataGenre.DATE =>
          dataFormatDate
        case DataGenre.DATETIME =>
          dataFormatDateTime
        case DataGenre.TIME =>
          dataFormatTime
      }
    cell.setCellStyle(style)
    cell.setCellValue(value)
  }

  @inline
  protected def SetFormula(cell: Cell, formula: String) {
    cell.setCellFormula(formula)
  }

  @inline
  protected def setValueBool(cell: Cell, value: Boolean) {
    cell.setCellValue(value)
  }

  @inline
  protected def setValueLong(cell: Cell, value: Long) {
    cell.setCellValue(value)
  }

  @inline
  protected def setValueNumber(cell: Cell, value: Double) {
    cell.setCellValue(value)
  }

  @inline
  protected def setValueText(cell: Cell, value: String) {
    cell.setCellValue(value)
  }


  protected lazy val dataFormatDateTime = {
    val style = wb.createCellStyle()
    style.setDataFormat(creationHelper.createDataFormat.getFormat("yyyy-MM-dd HH:mm:ss"))
    style
  }

  protected lazy val dataFormatDate = {
    val style = wb.createCellStyle()
    style.setDataFormat(creationHelper.createDataFormat.getFormat("yyyy-MM-dd"))
    style
  }

  protected lazy val dataFormatTime = {
    val style = wb.createCellStyle()
    style.setDataFormat(creationHelper.createDataFormat.getFormat("HH:mm:ss"))
    style
  }

  protected lazy val dataFormatMinus = {
    val style = wb.createCellStyle()
    style.setDataFormat(creationHelper.createDataFormat.getFormat("HH:mm"))
    style
  }

  protected lazy val dataFormatDateMinus = {
    val style = wb.createCellStyle()
    style.setDataFormat(creationHelper.createDataFormat.getFormat("yyyy-MM-dd HH:mm"))
    style
  }

  protected lazy val dataFormatMonth = {
    val style = wb.createCellStyle()
    style.setDataFormat(creationHelper.createDataFormat.getFormat("yyyy-MM"))
    style
  }

}