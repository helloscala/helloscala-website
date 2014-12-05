package yangbajing.common

import java.io._
import java.nio.ByteBuffer
import java.nio.file.{FileAlreadyExistsException, Files, Path, Paths}
import java.text.NumberFormat
import java.util.Locale

import org.bouncycastle.util.encoders.{Base64, Hex, UrlBase64}
import org.joda.time.{DateTime, LocalDate, LocalTime}

import scala.util.{Failure, Success, Try}
import scala.xml.{Node, PrettyPrinter, Utility}

object BaseY extends BaseY

trait BaseY
  extends TryUsingResource
  with MathHelper
  with TimeHelpers
  with BaseRandom
  with BaseImplicitly {

  val perDayMillis = 1000L * 60L * 60L * 24L
  val formatCurrency = NumberFormat.getCurrencyInstance(Locale.SIMPLIFIED_CHINESE)

  val xmlPrinter = new PrettyPrinter(120, 2)

  def xmlTrim(node: Node) = Utility.trim(node)

  def md5 = DigestHelpers("MD5")

  def sha1 = DigestHelpers("SHA1")

  def sha256 = DigestHelpers("SHA256")

  def sha512 = DigestHelpers("SHA512")

  lazy val emailer = java.util.regex.Pattern.compile( """\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*""")

  def encodeBase64(v: String) = new String(Base64.encode(v.getBytes))

  def decodeBase64(v: String) = new String(Base64.decode(v))

  def encodeBase64UrlSafe(v: String) = new String(UrlBase64.encode(v.getBytes))

  def decodeBase64UrlSafe(v: String) = new String(UrlBase64.decode(v))

  def toString(e: Throwable): String = {
    var sw: StringWriter = null
    try {
      sw = new StringWriter()
      val pw = new PrintWriter(sw)
      e.printStackTrace(pw)
      sw.toString
    } finally {
      if (sw ne null) sw.close()
    }
  }

  def toString(data: Array[Byte]) =
    new String(data)

  def toHexString(data: Array[Byte]): String = new String(Hex.encode(data))

  /**
   * 校验文件SHA值
   * @param path
   * @param shaType
   * @param buffer
   * @return
   */
  def digestSha(path: Path, shaType: ShaType.Value, buffer: ByteBuffer): ShaResult = {
    val md = getMeMessageDigest(shaType)
    val bc = Files.newByteChannel(path)

    buffer.clear()
    bc.read(buffer)
    buffer.flip()
    while (buffer.hasRemaining) {
      md.update(buffer)

      //      if (buffer.hasRemaining) buffer.compact()
      //      else buffer.clear()
      buffer.clear()

      bc.read(buffer)
      buffer.flip()
    }

    val shaValue = md.result
    bc.close()

    ShaResult(path, shaType, shaValue)
  }

  def getMeMessageDigest(shaType: ShaType.Value) = shaType match {
    case ShaType.Sha1 => sha1
    case ShaType.Sha256 => sha256
    case ShaType.Sha512 => sha512
  }

  @inline
  def nonBlank(v: Int): Boolean = v > 0

  @inline
  def nonBlank(v: Long): Boolean = v > 0L

  @inline
  def nonBlank(v: String): Boolean = !isBlank(v)

  @inline
  def isBlank(v: Int): Boolean = !nonBlank(v)

  @inline
  def isBlank(v: Long): Boolean = !nonBlank(v)

  @inline
  def isBlank(v: String): Boolean = (v eq null) || v.length == 0 || v.forall(_ == ' ')

  @inline
  def beforeDateTimeString() = formatDateTime print DateTime.now.minusDays(1)

  @inline
  def afterDateTimeString() = formatDateTime print DateTime.now.plusDays(1)

  @inline
  def curDateTimeString() = formatDateTime print DateTime.now

  @inline
  def curDateString() = formatDate print LocalDate.now

  @inline
  def curTimeString() = formatTime print LocalTime.now

  @inline
  def nextDateString() = formaterDate format new java.util.Date(System.currentTimeMillis() + perDayMillis)

  @inline
  def currentTimeMillis() = System.currentTimeMillis()

  @inline
  def currentTimeSeconds(): Long = System.currentTimeMillis() / 1000

  @inline
  def emailValidate(email: String): Boolean = {
    emailer.matcher(email).matches
  }

  @inline
  def mobileValidate(mobile: String): Boolean = {
    mobile.length == 11 && mobile.charAt(0) == '1' && mobile.forall(Character.isDigit)
  }

  @inline
  def ySha1(data: String): String =
    ySha1(data.getBytes)

  @inline
  def ySha1(data: Array[Byte]): String =
    new ByteArray2String(sha1.digest(data)).__HexString

  @inline
  def ySha256(data: String): String =
    ySha256(data.getBytes)

  @inline
  def ySha256(data: Array[Byte]): String =
    new ByteArray2String(sha256.digest(data)).__HexString

  @inline
  def ySha512(data: String): String =
    ySha512(data.getBytes)

  @inline
  def ySha512(data: Array[Byte]): String =
    new ByteArray2String(sha512.digest(data)).__HexString

  @inline
  def yMd5(data: String): String =
    yMd5(data.getBytes)

  @inline
  def yMd5(data: Array[Byte]): String =
    new ByteArray2String(md5.digest(data)).__HexString

  @inline
  def dumpDate[V <: java.util.Date](date: V) {
    println(formaterDate.format(date))
  }

  @inline
  def dumpDate(date: java.util.Calendar) {
    println(formaterDate.format(date.getTime))
  }

  @inline
  def isAlphabet(c: Char): Boolean =
    (c >= 'a' && c <= 'z') ||
      (c >= 'A' && c <= 'Z')

  @inline
  def asInt(a: Any) = A.orInt(a)

  @inline
  def asLong(a: Any) = A.orLong(a)

  /**
   * 判断文件是否存在，不存在则创建它
   * @param f 文件本地全路径
   * @return 成功返回Right(File对象)，失败返回Left(错误消息)
   */
  @inline
  def mkFile(f: String): Either[String, File] =
    mkFile(new File(f))

  /**
   * 判断文件是否存在，不存在则创建它
   * @param f 文件
   * @return 成功返回Right(File对象)，失败返回Left(错误消息)
   */
  def mkFile(f: File): Either[String, File] =
    f match {
      case file if file.exists() =>
        Right(file)

      case path if path.isDirectory =>
        if (path.mkdirs()) Right(path)
        else Left(s"创建$path 失败")

      case file =>
        val path = file.getParentFile

        if (path.exists()) Right(file)
        else if (path.mkdirs()) Right(file)
        else Left(s"创建$file 所在目录失败")
    }

  @inline
  def mkDir(dir: String): Try[Path] = mkDir(Paths.get(dir))

  def mkDir(dir: Path): Try[Path] =
    dir match {
      case _ if Files.isDirectory(dir) => Success(dir)
      case _ if Files.exists(dir) => Failure(new FileAlreadyExistsException(dir + " 已存在，但不是目录。"))
      case _ => Try(Files.createDirectories(dir))
    }


  /**
   * 保存文件到本地磁盘
   * @param f
   * @param func
   * @tparam R
   * @return
   */
  @throws[IOException]
  def saveWithFile[R](f: String)(func: OutputStream => R): Try[R] =
    Try(mkFile(f) match {
      case Right(file) =>
        var out: FileOutputStream = null

        try {
          out = new FileOutputStream(file)
          func(out)
        } finally
          if (out ne null) out.close()

      case Left(msg) =>
        throw new IOException(msg)
    })


}
