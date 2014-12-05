package yangbajing.common

trait EnumTrait {
  this: Enumeration =>

  lazy val tupleValues: List[(String, String)] = values.toList.map(v => v.id.toString -> v.toString)

  lazy val objTupleValues: List[(this.type#Value, String)] = values.toList.map(v => v -> v.toString)

  lazy val idTupleValues: List[(Int, String)] = values.toList.map(v => v.id -> v.toString)

  def existsByName(s: String): Boolean =
    values.exists(_.toString == s)

  def existsById(id: Int): Boolean =
    values.exists(_.id == id)

  def parse(i: Int): this.type#Value = apply(i)

  def parse(s: String): this.type#Value = withName(s)

  def parseById(s: String): this.type#Value = apply(s.toInt)

  def option(v: Any): Option[this.type#Value] =
    (v match {
      case i: Int => BaseY.tryOption(parse(i))
      case s: String => BaseY.tryOption(parse(s))
      case _ => None
    }) orElse (v match {
      case AsInt(i) => BaseY.tryOption(parse(i))
      case _ => None
    })

}
