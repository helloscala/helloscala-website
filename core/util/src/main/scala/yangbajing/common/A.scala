package yangbajing.common

import java.util.{Calendar, Date, UUID}

import org.joda.time.{LocalTime, LocalDate, DateTime}
import org.bson.types.ObjectId
import TryUsingResource.tryOption

object AsString {
  def unapply(v: Any): Option[String] = A.orString(v)
}

object AsObjectId {
  def unapply(v: Any): Option[ObjectId] = A.orObjectId(v)
}

object AsUUID {
  def unapply(v: Any): Option[UUID] = A.orUUID(v)
}

object AsInt {
  def unapply(v: Any): Option[Int] = A.orInt(v)
}

object AsLong {
  def unapply(v: Any): Option[Long] = A.orLong(v)
}

object AsDateTime {
  def unapply(v: Any): Option[DateTime] = A.orDateTime(v)
}

object AsChar {
  def unapply(v: Any): Option[Char] = A.orChar(v)
}

object AsByte {
  def unapply(v: Any): Option[Byte] = A.orByte(v)
}

object AsShort {
  def unapply(v: Any): Option[Short] = A.orShort(v)
}

object AsBigDecimal {
  def unapply(v: Any): Option[BigDecimal] = A.orBigDecimal(v)
}

object AsDouble {
  def unapply(v: Any): Option[Double] = A.orDouble(v)
}

object AsDate {
  def unapply(v: Any): Option[Date] = A.orDate(v)
}

object AsLocalTime {
  def unapply(v: Any): Option[LocalTime] = A.orLocalTime(v)
}

object AsBigInt {
  def unapply(v: Any): Option[BigInt] = A.orBigInt(v)
}

object AsBoolean {
  def unapply(v: Any): Option[Boolean] = A.orBoolean(v)
}

object A extends A

trait A extends {

  def eitherInt[R](d: Any)(func: => R): Either[Int, R] = d match {
    case v: Int => Left(v)
    case _ => Right(func)
  }

  def eitherString[R](d: Any)(func: => R): Either[String, R] = d match {
    case v: String => Left(v)
    case _ => Right(func)
  }

  def eitherBoolean[R](d: Any)(func: => R): Either[Boolean, R] = d match {
    case v: Boolean => Left(v)
    case _ => Right(func)
  }

  def eitherDouble[R](d: Any)(func: => R): Either[Double, R] = d match {
    case v: Double => Left(v)
    case _ => Right(func)
  }

  def eitherLong[R](d: Any)(func: => R): Either[Long, R] = d match {
    case v: Long => Left(v)
    case _ => Right(func)
  }

  def eitherObjectId[R](d: Any)(func: => R): Either[ObjectId, R] = d match {
    case v: ObjectId => Left(v)
    case _ => Right(func)
  }

  def eitherDate[R](d: Any)(func: => R): Either[Date, R] = d match {
    case v: Date => Left(v)
    case _ => Right(func)
  }

  def eitherUUID[R](d: Any)(func: => R): Either[UUID, R] = d match {
    case v: UUID => Left(v)
    case _ => Right(func)
  }

  def eitherDateTime[R](d: Any)(func: => R): Either[DateTime, R] = d match {
    case v: DateTime => Left(v)
    case _ => Right(func)
  }

  def getOrElse(d: Any, deft: => String): String = d match {
    case v: String => v
    case _ => deft
  }

  @inline
  def getOrElse(d: Any, deft: => Boolean): Boolean =
    orBoolean(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Byte): Byte =
    orByte(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Char): Char =
    orChar(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Short): Short =
    orShort(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Int): Int =
    orInt(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Long): Long =
    orLong(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => BigInt): BigInt =
    orBigInt(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Float): Float =
    orFloat(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Double): Double =
    orDouble(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => BigDecimal): BigDecimal =
    orBigDecimal(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => Date): Date =
    orDate(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => DateTime): DateTime =
    orDateTime(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => ObjectId): ObjectId =
    orObjectId(d) getOrElse deft

  @inline
  def getOrElse(d: Any, deft: => UUID): UUID =
    orUUID(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orByte(d: Any): Option[Byte] =
    d match {
      case b: Byte =>
        Some(b)

      case s: String =>
        tryOption(s.toByte)

      case _ =>
        None
    }

  @inline
  def orByte(d: Option[Any]): Option[Byte] =
    d flatMap (orByte _)

  @inline
  def orByte(d: Option[Any], deft: => Byte): Byte =
    orByte(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orChar(d: Any): Option[Char] =
    d match {
      case v: Char =>
        Some(v)

      case s: String if s.length == 1 =>
        Some(s.head)

      case _ =>
        None
    }

  @inline
  def orChar(d: Option[Any]): Option[Char] =
    d flatMap (orChar _)

  @inline
  def orChar(d: Option[Any], deft: => Char): Char =
    orChar(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orShort(d: Any): Option[Short] =
    d match {
      case v: Short =>
        Some(v)

      case s: String =>
        tryOption(s.toShort)

      case _ =>
        None
    }

  @inline
  def orShort(d: Option[Any]): Option[Short] =
    d flatMap (orShort _)

  @inline
  def orShort(d: Option[Any], deft: => Short): Short =
    orShort(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orFloat(d: Any): Option[Float] =
    d match {
      case v: Float =>
        Some(v)

      case s: String =>
        tryOption(s.toFloat)

      case c: Char =>
        Some(c.toFloat)

      case b: Byte =>
        Some(b.toShort)

      case _ =>
        None
    }

  @inline
  def orFloat(d: Option[Any]): Option[Float] =
    d flatMap (orFloat _)

  @inline
  def orFloat(d: Option[Any], deft: Float): Float =
    orFloat(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orInt(d: Any): Option[Int] =
    d match {
      case i: Int => Some(i)
      case s: Short => Some(s.toInt)
      case s: String if s != "" => tryOption(s.toInt)
      case l: Long => Some(l.toInt)
      case _ => None
    }

  @inline
  def orInt(d: Option[Any]): Option[Int] =
    d.flatMap(orInt _)

  @inline
  def orInt(d: Option[Any], deft: => Int): Int = orInt(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orBoolean(d: Any): Option[Boolean] =
    d match {
      case b: Boolean =>
        Some(b)

      case i: Int =>
        Some(if (i == 0) true else false)

      case s: String =>
        tryOption(s.toBoolean)

      case _ =>
        None
    }

  @inline
  def orBoolean(d: Option[Any]): Option[Boolean] =
    d.flatMap(orBoolean _)

  @inline
  def orBoolean(d: Option[Any], deft: => Boolean): Boolean = orBoolean(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orLong(d: Any): Option[Long] =
    d match {
      case l: Long =>
        Some(l)

      case i: Int =>
        Some(i.toLong)

      case s: String if s != "" =>
        tryOption(s.toLong)

      case _ =>
        None
    }

  @inline
  def orLong(d: Option[Any]): Option[Long] =
    d.flatMap(orLong _)

  @inline
  def orLong(d: Option[Any], deft: => Long): Long =
    orLong(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orBigInt(d: Any): Option[BigInt] =
    d match {
      case b: BigInt =>
        Some(b)

      case b: java.math.BigInteger =>
        Some(BigInt(b))

      case l: Long =>
        Some(BigInt(l))

      case s: String =>
        tryOption(BigInt(s))

      case _ =>
        None
    }

  @inline
  def orBigInt(d: Option[Any]): Option[BigInt] =
    d.flatMap(orBigInt _)

  @inline
  def orBigInt(d: Option[Any], deft: => BigInt): BigInt =
    orBigInt(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orString(d: Any): Option[String] =
    d match {
      case s: String =>
        Some(s)

      case s =>
        Some(d.toString)
    }

  @inline
  def orString(d: Option[Any]): Option[String] =
    d.flatMap(orString _)

  @inline
  def orString(d: Option[Any], deft: => String): String =
    orString(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orDouble(d: Any): Option[Double] =
    d match {
      case d: Double =>
        Some(d)

      case f: Float =>
        Some(f.toDouble)

      case s: String =>
        tryOption(s.toDouble)

      case _ =>
        None
    }

  @inline
  def orDouble(d: Option[Any]): Option[Double] =
    d.flatMap(orDouble _)

  @inline
  def orDouble(d: Option[Any], deft: => Double): Double =
    orDouble(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orDate(d: Any): Option[Date] =
    d match {
      case d: Date => // 包括 java.sql.Date, java.sql.Time, java.sql.Timestamp
        Some(d)

      case d: DateTime =>
        Some(d.toDate)

      case c: Calendar =>
        Some(c.getTime)

      case l: Long =>
        Some(new Date(l))

      case s: String =>
        TimeHelpers.parseDate(s)

      case _ =>
        None
    }

  @inline
  def orDate(d: Option[Any]): Option[Date] =
    d.flatMap(orDate _)

  @inline
  def orDate(d: Option[Any], deft: => Date): Date =
    orDate(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orLocalDate(d: Any): Option[LocalDate] =
    d match {
      case v: LocalDate =>
        Some(v)

      case v: DateTime =>
        Some(v.toLocalDate)

      case v: Date =>
        Some(new LocalDate(v.getTime))

      case l: Long =>
        Some(new LocalDate(l))

      case s: String =>
        TimeHelpers.parseLocalDate(s)

      case v: Calendar =>
        Some(new LocalDate(v.getTimeInMillis))

      case _ =>
        None
    }

  @inline
  def orLocalDate(d: Option[Any]): Option[LocalDate] =
    d.flatMap(orLocalDate _)

  @inline
  def orLocalDate(d: Option[Any], deft: => LocalDate): LocalDate =
    orLocalDate(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orLocalTime(d: Any): Option[LocalTime] =
    d match {
      case v: LocalTime =>
        Some(v)

      case v: DateTime =>
        Some(v.toLocalTime)

      case v: Date =>
        Some(new LocalTime(v.getTime))

      case l: Long =>
        Some(new LocalTime(l))

      case s: String =>
        TimeHelpers.parseLocalTime(s)

      case v: Calendar =>
        Some(new LocalTime(v.getTimeInMillis))

      case _ =>
        None
    }

  @inline
  def orLocalTime(d: Option[Any]): Option[LocalTime] =
    d flatMap orLocalTime

  @inline
  def orLocalTime(d: Option[Any], deft: => LocalTime): LocalTime =
    orLocalTime(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orDateTime(d: Any): Option[DateTime] =
    d match {
      case d: DateTime =>
        Some(d)

      case d: Date => // 包括 java.sql.Date, java.sql.Time, java.sql.Timestamp
        Some(new DateTime(d.getTime))

      case s: String =>
        TimeHelpers.parseDateTime(s)

      case l: Long =>
        Some(new DateTime(l))

      case c: Calendar =>
        Some(new DateTime(c.getTimeInMillis))

      case Some(a) =>
        orDateTime(a)
      case _ =>
        None
    }

  @inline
  def orDateTime(d: Option[Any], deft: => DateTime): DateTime =
    orDateTime(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orObjectId(d: Any): Option[ObjectId] =
    d match {
      case v: ObjectId =>
        Some(v)

      case s: String if ObjectId.isValid(s) =>
        Some(new ObjectId(s))

      case _ =>
        None
    }

  @inline
  def orObjectId(d: Option[Any]): Option[ObjectId] =
    d flatMap orObjectId

  @inline
  def orObjectId(d: Option[Any], deft: => ObjectId): ObjectId = orObjectId(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orBigDecimal(d: Any): Option[BigDecimal] =
    d match {
      case v: BigDecimal =>
        Some(v)

      case v: java.math.BigDecimal =>
        Some(BigDecimal(v))

      case d: Double =>
        Some(BigDecimal(d))

      case s: String =>
        tryOption(BigDecimal(s))

      case a: Array[Char] =>
        tryOption(BigDecimal(a))

      case b: BigInt =>
        Some(BigDecimal(b))

      case _ =>
        None
    }

  def orBigDecimal(d: Option[Any]): Option[BigDecimal] =
    d flatMap (orBigDecimal _)

  def orBigDecimal(d: Option[Any], deft: => BigDecimal): BigDecimal =
    orBigDecimal(d) getOrElse deft

  /////////////////////////////////////////////////////////////////////////
  def orUUID(d: Any): Option[UUID] =
    d match {
      case v: UUID =>
        Some(v)

      case s: String =>
        tryOption(UUID.fromString(s))

      case (most: Long, least: Long) =>
        tryOption(new UUID(most, least))

      //      case a: Array[Byte] =>
      //        tryOption(UUID.nameUUIDFromBytes(a))

      case _ =>
        None
    }

  @inline
  def orUUID(d: Option[Any]): Option[UUID] =
    d flatMap (orUUID _)

  @inline
  def orUUID(d: Option[Any], deft: => UUID): UUID =
    orUUID(d) getOrElse deft

}
