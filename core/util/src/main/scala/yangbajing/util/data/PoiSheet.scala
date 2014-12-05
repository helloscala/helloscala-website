package yangbajing.util.data

import org.apache.poi.ss.usermodel.{Sheet, Workbook}

private class PoiSheet[T <: Workbook](
                                       val wb: T,
                                       header: Seq[DataLabel],
                                       tables: Seq[Map[String, Any]],
                                       sheetName: String) extends AbstractPoi {

  def execute: Sheet = {
    val sheet = wb.createSheet(sheetName)
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

    sheet
  }

}
