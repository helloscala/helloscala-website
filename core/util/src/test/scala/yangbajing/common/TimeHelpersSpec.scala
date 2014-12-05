package yangbajing.common

import org.scalatest._

import org.joda.time._

class TimeHelpersSpec extends FlatSpec with Matchers with TimeHelpers {

  "TimeHelper" should "formater" in {
    formatMonth.parseLocalDate("2013-5") should be(new LocalDate(2013, 5, 1))
    formatDate.parseLocalDate("2013-05-07") should be(new LocalDate(2013, 5, 7))
    formatTime.parseLocalTime("11:2:3") should be(new LocalTime(11, 2, 3))
    formatMinus.parseLocalTime("11:2") should be(new LocalTime(11, 2))
    formatTimeMillis.parseLocalTime("11:2:0.2") should be(new LocalTime(11, 2, 0, 200))
  }

}

