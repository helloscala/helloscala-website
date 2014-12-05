package yangbajing.common.io

import java.io._

object IOHelpers {
  def read[R](file: String)(func: InputStream => R): Either[IOException, R] = {
    var in: FileInputStream = null
    try {
      in = new FileInputStream(file)
      Right(func(in))
    } catch {
      case e: IOException =>
        Left(e)
    } finally {
      if (in != null)
        in.close()
    }
  }
}
