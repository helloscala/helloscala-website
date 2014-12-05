package yangbajing.common

trait IdName[ID] extends BaseId[ID] {
  self =>

  def id: ID

  def name: String

  def toIdName = new IdName[ID] {
    def id = self.id

    def name = self.name

    override def toString = s"IdName[${id.getClass.getSimpleName}]($id, $name)"
  }
}
