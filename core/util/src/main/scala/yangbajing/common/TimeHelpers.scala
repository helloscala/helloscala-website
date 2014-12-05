package yangbajing.common

import java.util.{Locale, Calendar, Date}
import java.sql.Timestamp
import java.text.SimpleDateFormat

import org.joda.time._
import org.joda.time.format.DateTimeFormat

object TimeHelpers extends TimeHelpers

trait TimeHelpers extends TryUsingResource {
  lazy val formatYear = DateTimeFormat.forPattern("yyyy")

  lazy val formatDayOfMonth = DateTimeFormat.forPattern("MM-dd")

  lazy val formatMonth = DateTimeFormat.forPattern("yyyy-MM")

  lazy val formatDate = DateTimeFormat.forPattern("yyyy-MM-dd")

  lazy val formatDateHour = DateTimeFormat.forPattern("yyyy-MM-dd HH")

  lazy val formatDateMinus = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

  val formatDateTime = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")

  lazy val formatDateTimeWeak = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss E")

  lazy val formatDateTimeMillis = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS")

  lazy val formatMinus = DateTimeFormat.forPattern("HH:mm")

  lazy val formatTime = DateTimeFormat.forPattern("HH:mm:ss")

  lazy val formatTimeWeek = DateTimeFormat.forPattern("HH:mm:ss E")

  lazy val formatTimeMillis = DateTimeFormat.forPattern("HH:mm:ss.SSS")

  lazy val formatInternetDate =
    DateTimeFormat.forPattern("EEE, d MMM yyyy HH:mm:ss 'GMT'").withLocale(Locale.US).withZone(DateTimeZone.UTC)

  object TimeVals {
    val months = (1 to 12).toVector

    val hours = (0 to 23).toVector

    val minus = (0 to 59).toVector
  }

  def dayOfWeekMin = DateTime.now().dayOfWeek().withMinimumValue().toDateTime

  def dayOfWeekMax = DateTime.now().dayOfWeek().withMaximumValue().toDateTime

  def dayOfMonthMin = DateTime.now().dayOfMonth().withMinimumValue().toDateTime

  def dayOfMonthMax = DateTime.now().dayOfMonth().withMaximumValue().toDateTime

  def betweenNow(time: DateTime): Int =
    Minutes.minutesBetween(time, DateTime.now).getMinutes

  def weeksDateTime(first: DateTime, last: DateTime): Vector[DateTime] = {
    val _last = first.dayOfWeek().withMaximumValue
    val duration = new Duration(first, if (last.isAfter(_last)) _last else last)
    (0 to duration.toStandardDays.getDays).map(first.plusDays).toVector
  }

  def weeks(first: LocalDate, last: LocalDate): Vector[LocalDate] = {
    val _last = first.dayOfWeek().withMaximumValue
    val duration = new Duration(first.toDateTimeAtStartOfDay,
      if (last.isAfter(_last)) _last.toDateTimeAtStartOfDay else last.toDateTimeAtStartOfDay)
    (0 to duration.toStandardDays.getDays).map(first.plusDays).toVector
  }

  def monthsDateTime(first: DateTime, last: DateTime): Vector[DateTime] = {
    val _last = first.dayOfMonth().withMaximumValue
    val duration = new Duration(first, if (last.isAfter(_last)) _last else last)
    (0 to duration.toStandardDays.getDays).map(first.plusDays).toVector
  }

  def months(first: LocalDate, last: LocalDate): Vector[LocalDate] = {
    val _last = first.dayOfMonth().withMaximumValue
    val duration = new Duration(first.toDateTimeAtStartOfDay,
      if (last.isAfter(_last)) _last.toDateTimeAtStartOfDay else last.toDateTimeAtStartOfDay)
    (0 to duration.toStandardDays.getDays).map(first.plusDays).toVector
  }

  def years(first: DateTime): Vector[Vector[DateTime]] =
    (0 until 12).map {
      i =>
        val begin = first.plusMonths(i)
        monthsDateTime(begin, begin.dayOfMonth().withMaximumValue)
    }.toVector

  def yearsLocalDate(first: LocalDate): Vector[Vector[LocalDate]] =
    (0 until 12).map {
      i =>
        val begin = first.plusMonths(i)
        months(begin, begin.dayOfMonth().withMaximumValue)
    }.toVector

  def calendarOfMonth(first: LocalDate, last: LocalDate): Vector[Vector[LocalDate]] =
    _weeksOfMonth(first, last).map(v => weeks(v._1, v._2))

  def calendarOfMonthDateTime(first: DateTime, last: DateTime): Vector[Vector[DateTime]] =
    _weeksOfMonth(first.toLocalDate, last.toLocalDate).map(v =>
      weeksDateTime(v._1.toDateTimeAtStartOfDay, v._2.toDateTimeAtStartOfDay))

  private def _weeksOfMonth(first: LocalDate, _last: LocalDate): Vector[(LocalDate, LocalDate)] = {
    val last = {
      val tmp = first.dayOfMonth().withMaximumValue
      if (_last isAfter tmp) tmp
      else _last
    }

    @volatile
    var tag = true

    val tmps = collection.mutable.ArrayBuffer(first -> first.dayOfWeek().withMaximumValue())

    while (tag) {
      val begin = tmps.last._2.plusDays(1)
      val end = begin.plusDays(6)

      if (begin.isAfter(last))
        tag = false
      else if (end.isBefore(last))
        tmps += (begin -> end)
      else {
        tmps += (begin -> last)
        tag = false
      }
    }

    tmps.toVector
  }


  def parseDate(v: String): Option[Date] =
    parseMillis(v) map (new Date(_))

  def parseCalendar(v: String): Option[Calendar] =
    parseMillis(v) map {
      l =>
        val c = Calendar.getInstance()
        c.setTimeInMillis(l)
        c
    }

  def parseSqlTimestamp(v: String): Option[Timestamp] =
    parseMillis(v) map (new Timestamp(_))

  def parseSqlDate(v: String): Option[java.sql.Date] =
    parseLocalDate(v) map (d => new java.sql.Date(d.toDate.getTime))

  def parseSqlTime(v: String): Option[java.sql.Time] =
    parseLocalTime(v) map (t => new java.sql.Time(t.toDateTimeToday.getMillis))

  def parseDateTime(v: String): Option[DateTime] =
    parseMillis(v) map (new DateTime(_))

  def parseLocalDate(v: String): Option[LocalDate] =
    tryOption(formatDate.parseLocalDate(v)) orElse
      tryOption(formatMonth.parseLocalDate(v)) orElse
      tryOption(formatYear.parseLocalDate(v))

  def parseLocalTime(v: String): Option[LocalTime] =
    tryOption(formatTime.parseLocalTime(v)) orElse
      tryOption(formatMinus.parseLocalTime(v)) orElse
      tryOption(formatTimeMillis.parseLocalTime(v)) orElse
      tryOption(formatTimeWeek.parseLocalTime(v))

  def parseMillis(v: String): Option[Long] =
    tryOption(formatDateTime.parseMillis(v)) orElse
      tryOption(formatDateMinus.parseMillis(v)) orElse
      tryOption(formatDateTimeMillis.parseMillis(v)) orElse
      tryOption(formatDateTimeWeak.parseMillis(v)) orElse
      tryOption(formatDate.parseMillis(v)) orElse
      tryOption(formatMonth.parseMillis(v)) orElse
      tryOption(formatYear.parseMillis(v))

  import DateTimeConstants._

  def chineseWeek(d: DateTime): String = d.getDayOfWeek match {
    case SUNDAY => "星期日"
    case MONDAY => "星期一"
    case TUESDAY => "星期二"
    case WEDNESDAY => "星期三"
    case THURSDAY => "星期四"
    case FRIDAY => "星期五"
    case SATURDAY => "星期六"
  }


  val formaterDateTimeWeak: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E")
  val formaterDateTime: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  lazy val formaterDateTimeMillis: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

  lazy val formaterDate: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
  lazy val formaterMonth: SimpleDateFormat = new SimpleDateFormat("yyyy-MM")

  lazy val formaterMinute: SimpleDateFormat = new SimpleDateFormat("HH:mm")
  lazy val formaterTime: SimpleDateFormat = new SimpleDateFormat("HH:mm:ss")
  lazy val formaterTimeMillis: SimpleDateFormat = new SimpleDateFormat("HH:mm:ss.SSS")
}
