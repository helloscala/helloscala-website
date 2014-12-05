package yangbajing.common

import org.bson.types.ObjectId

trait BaseId[ID] {
  self =>
  def id: ID

  def toBaseId = new BaseId[ID] {
    def id = self.id

    override def toString = s"BaseId[${id.getClass.getSimpleName}]($id)"
  }
}

case class IdNameInt(id: Int, name: String) extends IdName[Int]

case class IdNameLong(id: Long, name: String) extends IdName[Long]

case class IdNameString(id: String, name: String) extends IdName[String]

case class IdNameObjectId(id: ObjectId, name: String) extends IdName[ObjectId]

