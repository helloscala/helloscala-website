package yangbajing.common

import java.nio.file.Path
import org.bouncycastle.util.encoders.Hex

/**
 * 计算sha值的结果
 * Created by devuser on 2014/4/29.
 */
case class ShaResult(path: Path, shaType: ShaType.Value, shaResult: Array[Byte]) {
  def hexSha = Hex.toHexString(shaResult)
}
