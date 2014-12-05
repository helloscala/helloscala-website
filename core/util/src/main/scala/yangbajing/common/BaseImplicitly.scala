package yangbajing.common

import scala.language.implicitConversions

import org.joda.time.DateTime
import java.util.Date

object BaseImplicitlys extends BaseImplicitly

trait BaseImplicitly {
  implicit def _yByteArray2String(data: Array[Byte]) = new ByteArray2String(data)

  implicit def _yTuple22ToKV[K, V](tuple2: Tuple2[K, V]) = new KV(tuple2._1, tuple2._2)

  implicit def _yTuple32ToXYZ[X, Y, Z](tuple3: Tuple3[X, Y, Z]) = XYZ(tuple3._1, tuple3._2, tuple3._3)

  implicit def _yTuple22TupleRichApi[K, V](tuple2: Tuple2[K, V]) = new Tuple2RichApi(tuple2)

  implicit def _yDate2DateRichApi(date: Date) = new DateRichApi(date)

  implicit def _yDateTime2DateTimeRichApi(date: DateTime) = new DateTimeRichApi(date)
}

sealed class Tuple2RichApi[K, V](tuple2: Tuple2[K, V]) {
  def key = tuple2._1
  def value = tuple2._2
}
