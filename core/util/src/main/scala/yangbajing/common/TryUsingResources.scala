package yangbajing.common

import java.io._

import scala.xml.Elem
import scala.collection.GenTraversableOnce

object TryUsingResource extends TryUsingResource

/**
 * 仿Java 7 try-catch-sources 和 C# using 方法的自动资源管理
 *
 * 需要Java 7和AutoClosable支持
 */
trait TryUsingResource {

  @inline
  def option[T <: CharSequence](s: => T): Option[T] = {
    val v = s
    if (v.eq(null) || v.length == 0)
      None
    else
      Some(v)
  }

  @inline
  def option(i: Int): Option[Int] =
    if (i == 0) None else Some(i)

  @inline
  def option(l: Long): Option[Long] =
    if (l == 0L) None else Some(l)

  @inline
  def optionGen[T <: GenTraversableOnce[_]](s: => T): Option[T] =
    Option(s).filterNot(_.isEmpty)

  @inline
  def tryEither[R](f: => R): Either[Exception, R] =
    try {
      Right(f)
    } catch {
      case e: Exception =>
        Left(e)
    }

  @inline
  def tryOption[R](f: => R): Option[R] =
    try {
      Some(f)
    } catch {
      case e: Exception =>
        None
    }

  def tryFlatOption[R](f: => Option[R]): Option[R] =
    try f
    catch {
      case _: Exception =>
        None
    }

  type TClose = Closeable //{ def close(): Unit }

  @inline
  def closeQuietly[A <: TClose](params: A*) {
    for (p <- params if p != null) {
      try {
        p.close
      } catch {
        case _: Throwable =>
      }
    }
  }

  @inline
  def closeQuietlyOption[A <: TClose](params: Option[A]*) {
    for (
      po <- params;
      p <- po if p != null
    ) {
      try {
        p.close
      } catch {
        case _: Throwable =>
      }
    }
  }

  @inline
  def closeException[A <: TClose](params: A*)(fe: PartialFunction[Throwable, Unit]): Unit = params.foreach(p =>
    try {
      if (p != null) p.close
    } catch {
      fe
    })

  def tryxml[R](file: File, charset: String = "UTF-8")(func: Elem => R): R =
    tryxml(new FileInputStream(file), charset)(func)

  def tryxml[R](in: InputStream, charset: String)(func: Elem => R): R =
    tryclose(new InputStreamReader(in, charset)) {
      is =>
        func(xml.XML.load(is))
    }

  object tryclose {
    def apply[A <: TClose, R](a: => A)(f: A => R): R = try {
      f(a)
    } finally {
      closeQuietly(a)
    }

    def apply[A <: TClose, B <: TClose, R](a: => A, b: => B)(f: (A, B) => R): R = try {
      f(a, b)
    } finally {
      closeQuietly(b, a)
    }

    def apply[A <: TClose, B <: TClose, C <: TClose, R](a: => A, b: => B, c: => C)(f: (A, B, C) => R): R = try {
      f(a, b, c)
    } finally {
      closeQuietly(c, b, a)
    }

    def apply[A <: TClose, B <: TClose, C <: TClose, D <: TClose, R](a: => A, b: => B, c: => C, d: => D)(f: (A, B, C, D) => R): R = try {
      f(a, b, c, d)
    } finally {
      closeQuietly(d, c, b, a)
    }

    def apply[A <: TClose, B <: TClose, C <: TClose, D <: TClose, E <: TClose, R](a: => A, b: => B, c: => C, d: => D, e: => E)(f: (A, B, C, D, E) => R): R = try {
      f(a, b, c, d, e)
    } finally {
      closeQuietly(e, d, c, b, a)
    }
  }

  /**
   * 类似Java 7新加try-with-resources功能。
   * tryusing-capture
   *
   * （注：最多支持5个参数，多于5个的话应该考虑重构代码了。）
   */
  object tryusing {
    def apply[A <: TClose, R](a: => A)(f: A => R): WithCapture1[A, R] =
      new WithCapture1(a, f)

    def apply[A <: TClose, B <: TClose, R](a: => A, b: => B)(f: (A, B) => R): WithCapture2[A, B, R] =
      new WithCapture2(a, b, f)

    def apply[A <: TClose, B <: TClose, C <: TClose, R](a: => A, b: => B, c: => C)(f: (A, B, C) => R): WithCapture3[A, B, C, R] =
      new WithCapture3(a, b, c, f)

    def apply[A <: TClose, B <: TClose, C <: TClose, D <: TClose, R](a: => A, b: => B, c: => C, d: => D)(f: (A, B, C, D) => R): WithCapture4[A, B, C, D, R] =
      new WithCapture4(a, b, c, d, f)

    def apply[A <: TClose, B <: TClose, C <: TClose, D <: TClose, E <: TClose, R](a: => A, b: => B, c: => C, d: => D, e: => E)(f: (A, B, C, D, E) => R): WithCapture5[A, B, C, D, E, R] =
      new WithCapture5(a, b, c, d, e, f)

    class WithCapture1[A <: TClose, R](a: => A, f: A => R) {
      def capture(fe: PartialFunction[Throwable, R]): R = {
        var ax: Option[A] = None
        try {
          ax = Some(a)
          f(ax.get)
        } catch {
          case t: Throwable => fe(t)
        } finally {
          closeQuietlyOption(ax)
        }
      }
    }

    class WithCapture2[A <: TClose, B <: TClose, R](a: => A, b: => B, f: (A, B) => R) {
      def capture(fe: PartialFunction[Throwable, R]): R = {
        var ax: Option[A] = None
        var bx: Option[B] = None
        try {
          ax = Some(a)
          bx = Some(b)
          f(ax.get, bx.get)
        } catch {
          case t: Throwable => fe(t)
        } finally {
          closeQuietlyOption(bx, ax)
        }
      }
    }

    class WithCapture3[A <: TClose, B <: TClose, C <: TClose, R](a: => A, b: => B, c: => C, f: (A, B, C) => R) {
      def capture(fe: PartialFunction[Throwable, R]): R = {
        var ax: Option[A] = None
        var bx: Option[B] = None
        var cx: Option[C] = None
        try {
          ax = Some(a)
          bx = Some(b)
          cx = Some(c)
          f(ax.get, bx.get, cx.get)
        } catch {
          case t: Throwable => fe(t)
        } finally {
          closeQuietlyOption(cx, bx, ax)
        }
      }
    }

    class WithCapture4[A <: TClose, B <: TClose, C <: TClose, D <: TClose, R](a: => A, b: => B, c: => C, d: => D, f: (A, B, C, D) => R) {
      def capture(fe: PartialFunction[Throwable, R]): R = {
        var ax: Option[A] = None
        var bx: Option[B] = None
        var cx: Option[C] = None
        var dx: Option[D] = None
        try {
          ax = Some(a)
          bx = Some(b)
          cx = Some(c)
          dx = Some(d)
          f(ax.get, bx.get, cx.get, dx.get)
        } catch {
          case t: Throwable => fe(t)
        } finally {
          closeQuietlyOption(dx, cx, bx, ax)
        }
      }
    }

    class WithCapture5[A <: TClose, B <: TClose, C <: TClose, D <: TClose, E <: TClose, R](a: => A, b: => B, c: => C, d: => D, e: => E, f: (A, B, C, D, E) => R) {
      def capture(fe: PartialFunction[Throwable, R]): R = {
        var ax: Option[A] = None
        var bx: Option[B] = None
        var cx: Option[C] = None
        var dx: Option[D] = None
        var ex: Option[E] = None
        try {
          ax = Some(a)
          bx = Some(b)
          cx = Some(c)
          dx = Some(d)
          ex = Some(e)
          f(ax.get, bx.get, cx.get, dx.get, ex.get)
        } catch {
          case t: Throwable => fe(t)
        } finally {
          closeQuietlyOption(ex, dx, cx, bx, ax)
        }
      }
    }

  }

}
