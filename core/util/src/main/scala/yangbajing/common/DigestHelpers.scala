package yangbajing.common

import java.io.InputStream
import java.security.{Security, MessageDigest}
import org.bouncycastle.jce.provider.BouncyCastleProvider

object DigestHelpers {
  Security.addProvider(new BouncyCastleProvider)

  def apply(digestName: String) =
    new DigestHelpers(MessageDigest.getInstance(digestName, BC))

  def digest(md: MessageDigest, in: InputStream): Array[Byte] = {
    val buffer = (0 until 1024).map(_.toByte).toArray
    var rn = in.read(buffer, 0, STREAM_BUFFER)
    while (rn > -1) {
      md.update(buffer, 0, rn)
      rn = in.read(buffer, 0, STREAM_BUFFER)
    }
    md.digest()
  }

  private val BC = "BC"
  private val STREAM_BUFFER = 1024
}

class DigestHelpers private(md: MessageDigest) {
  def provider = md.getProvider

  def algorithm = md.getAlgorithm

  def digestLength = md.getDigestLength

  def length = digestLength

  def reset = {
    md.reset()
    this
  }

  def result = md.digest

  def digest(data: Array[Byte]) = md.digest(data)

  def digest(data: Array[Byte], x: Int, y: Int) =
    md.digest(data, x, y)

  def update(data: Array[Byte], x: Int, y: Int) = {
    md.update(data, x, y)
    this
  }

  def update(buffer: java.nio.ByteBuffer) = {
    md.update(buffer)
    this
  }

  def update(data: Array[Byte]) = {
    md.update(data)
    this
  }

  def update(b: Byte) = {
    md.update(b)
    this
  }
}
