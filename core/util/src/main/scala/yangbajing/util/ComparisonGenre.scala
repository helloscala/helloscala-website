package yangbajing.util

object ComparisonGenre extends Enumeration {
  val Gte = Value(1, "$gte")
  val Gt = Value("$gt")
  val Lte = Value("$lte")
  val Lt = Value("$lt")
}
