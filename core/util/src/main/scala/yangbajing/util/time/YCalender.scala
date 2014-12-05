package yangbajing.util.time

import org.joda.time._

import yangbajing.common.TimeHelpers
import java.util.{Calendar, Date}

object YCalendar {
  def apply(now: DateTime): YCalendar = new YCalendar(now)

  def apply(now: Date): YCalendar = apply(new DateTime(now.getTime))

  def apply(now: LocalDate): YCalendar = apply(now.toDateTimeAtStartOfDay)

  def apply(calendar: Calendar): YCalendar = apply(new DateTime(calendar.getTimeInMillis))
}

/**
 * 此类将基于传入参数进行计算
 *
 * @param now
 */
class YCalendar(val now: DateTime) {

  /**
   * 周的所有日期
   * @return
   */
  def weeksDateTime: Vector[DateTime] =
    TimeHelpers.weeksDateTime(now.dayOfWeek().withMinimumValue, now.dayOfWeek().withMaximumValue)


  def weeks: Vector[LocalDate] = {
    val first = now.toLocalDate.dayOfWeek().withMinimumValue
    TimeHelpers.weeks(first, first.dayOfWeek().withMaximumValue)
  }


  /**
   * 月的所有日期
   * @return
   */
  def monthsDateTime: Vector[DateTime] =
    TimeHelpers.monthsDateTime(now.dayOfMonth().withMinimumValue, now.dayOfMonth().withMaximumValue)


  def months: Vector[LocalDate] = {
    val first = now.toLocalDate.dayOfMonth().withMinimumValue
    TimeHelpers.months(first, first.dayOfMonth().withMaximumValue)
  }

  /**
   * 日历表
   *
   * @return
   */
  def calendar: Vector[Vector[Vector[LocalDate]]] = {
    val first = now.toLocalDate.dayOfYear().withMinimumValue()

    (0 until 12).map {
      i =>
        val begin = first.plusMonths(i)
        TimeHelpers.calendarOfMonth(begin, begin.dayOfMonth().withMaximumValue())
    }.toVector
  }

  def calendarFull: Vector[Vector[Vector[LocalDate]]] =
    calendar.map(calendarOfMonthFull(_))

  def calendarDateTime: Vector[Vector[Vector[DateTime]]] = {
    val first = now.dayOfYear().withMinimumValue()

    (0 until 12).map {
      i =>
        val begin = first.plusMonths(i)
        TimeHelpers.calendarOfMonthDateTime(begin, begin.dayOfMonth().withMaximumValue())
    }.toVector
  }

  /**
   * 月份日历表
   * @return
   */
  def calendarOfMonth: Vector[Vector[LocalDate]] = {
    val first = now.toLocalDate.dayOfMonth().withMinimumValue
    TimeHelpers.calendarOfMonth(first, first.dayOfMonth().withMaximumValue)
  }

  def calendarOfMonthFull: Vector[Vector[LocalDate]] =
    calendarOfMonthFull(calendarOfMonth)

  def calendarOfMonthFull(vs: Vector[Vector[LocalDate]]): Vector[Vector[LocalDate]] = {
    val buffer = collection.mutable.ArrayBuffer[Vector[LocalDate]]()

    vs.size match {
      case 4 =>
        buffer += (1 to 7).reverse.map(vs.head.head.minusDays _).toVector

        buffer ++= vs

        buffer += (1 to 7).reverse.map(buffer.last.last.plusDays _).toVector

      case 5 =>
        if (vs.head.size < 7)
          buffer += ((1 to (7 - vs.head.size)).reverse.map(vs.head.head.minusDays _) ++ vs.head).toVector
        else
          buffer += vs.head

        for (i <- 1 to 3) buffer += vs(i)

        if (vs.last.size < 7)
          buffer += (vs.last ++ (1 to (7 - vs.last.size)).map(vs.last.last.plusDays _)).toVector
        else
          buffer += vs.last

        buffer += (1 to 7).map(buffer.last.last.plusDays _).toVector

      case 6 =>
        if (vs.head.size < 7)
          buffer += ((1 to (7 - vs.head.size)).reverse.map(vs.head.head.minusDays _) ++ vs.head).toVector
        else
          buffer += vs.head

        for (i <- 1 to 4) buffer += vs(i)

        if (vs.last.size < 7)
          buffer += (vs.last ++ (1 to (7 - vs.last.size)).map(vs.last.last.plusDays _)).toVector
        else
          buffer += vs.last

      case _ =>
        throw new IllegalArgumentException("月分日历表周数不正确！")

    }

    buffer.toVector
  }


  def calendarOfMonthDateTime: Vector[Vector[DateTime]] = {
    val first = now.dayOfMonth().withMinimumValue
    TimeHelpers.calendarOfMonthDateTime(first, first.dayOfMonth().withMaximumValue)
  }

  /**
   * 一年有所有日期
   */
  def years: Vector[Vector[DateTime]] =
    TimeHelpers.years(now.dayOfYear().withMinimumValue)


  def yearsLocalDate: Vector[Vector[LocalDate]] =
    TimeHelpers.yearsLocalDate(now.toLocalDate.dayOfYear().withMinimumValue)

}