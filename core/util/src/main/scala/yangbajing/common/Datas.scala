package yangbajing.common


case class KV[K, +V](k: K, v: V)

case class XY[X, +Y](x: X, y: Y)

case class XYZ[X, +Y, +Z](x: X, y: Y, z: Z)

case class NameValue[N, +V](name: N, value: V)

case class KeyValue[K, +V](key: K, value: V)

case class NVString(n: String, v: String)
