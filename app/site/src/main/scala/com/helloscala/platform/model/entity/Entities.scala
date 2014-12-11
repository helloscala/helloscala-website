package com.helloscala.platform.model.entity

import com.helloscala.platform.common.{SuperId, SexTypes, SexType, ImageType}
import com.helloscala.platform.driver.MyJdbcDriver
import com.helloscala.platform.util.SqlDbConf
import org.joda.time.DateTime

class Entities(conf: SqlDbConf, val driver: MyJdbcDriver.DriverType) {

  import driver.simple._

  trait MyTable[PK, T] {
    this: Table[T] =>
    val id: Column[PK]
    val created_at: Column[DateTime]

    val SUPER_ID_TYPE = O.DBType("char(24)")
  }

  class TableTag(tag: Tag) extends Table[MTag](tag, "m_tag") with MyTable[Long, MTag] {
    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    val content = column[String]("content")
    val created_at = column[DateTime]("created_at")

    val _idxContent = index(tableName + "_idx_content", content, true)

    def * = (id.?, content, created_at) <>(MTag.tupled, MTag.unapply)
  }

  val tTag = TableQuery[TableTag]

  class TableUser(tag: Tag) extends Table[MUser](tag, "m_user") with MyTable[Long, MUser] {
    val id = column[Long]("id", O.PrimaryKey, O.AutoInc)
    val email = column[String]("email")
    val username = column[String]("username", O.Nullable)
    val nick = column[String]("nick", O.Nullable)
    val role_ids = column[List[Int]]("role_ids", O.Default(Nil))
    val sex = column[SexType]("sex", O.Default(SexTypes.Unknown))
    val portrait = column[String]("portrait", O.Nullable)
    val attrs = column[Map[String, String]]("attrs")
    val created_at = column[DateTime]("created_at")

    val _idxUsername = index(tableName + "_idx_username", username, true)
    val _idxEmail = index(tableName + "_idx_email", email, true)

    def * = (id.?, email, username.?, nick.?, role_ids, sex, portrait.?, attrs, created_at
      ) <>(MUser.tupled, MUser.unapply)
  }

  val tUser = TableQuery[TableUser]

  class TableUserPassword(tag: Tag) extends Table[MUserPassword](tag, "m_user_password") {
    val id = column[Long]("id", O.PrimaryKey)
    val salt = column[String]("salt")
    val password = column[String]("password")

    def * = (id, salt, password) <>(MUserPassword.tupled, MUserPassword.unapply)
  }

  val tUserPassword = TableQuery[TableUserPassword]

  class TableImage(tag: Tag) extends Table[MImage](tag, "m_image") with MyTable[String, MImage] {
    val id = column[String]("id", O.PrimaryKey)
    val media_type = column[ImageType]("media_type")
    val length = column[Int]("length")
    val created_at = column[DateTime]("created_at")

    def * = (id, media_type, length, created_at) <>(MImage.tupled, MImage.unapply)
  }

  val tImage = TableQuery[TableImage]

  class TableDocument(tag: Tag) extends Table[MDocument](tag, "m_document") with MyTable[SuperId, MDocument] {
    val id = column[SuperId]("id", O.PrimaryKey, SUPER_ID_TYPE)
    val title = column[String]("title")
    val author = column[Long]("author")
    val content = column[String]("content", O.DBType(driver.DB_TYPE_TEXT))
    val description = column[String]("description", O.Nullable)
    val slug = column[String]("slug", O.Nullable)
    val allow_anonymous = column[Boolean]("allow_anonymous", O.Default(false))
    val tags = column[List[Long]]("tags")
    val attrs = column[Map[String, String]]("attrs")
    val created_at = column[DateTime]("created_at")

    val _idxSlug = index(tableName + "_idx_slug", slug, true)
    val _fkUser = foreignKey(tableName + "_fk_user", author, tUser)(_.id)

    def * = (id.?, title, author, content, description.?, slug.?, allow_anonymous, tags, attrs, created_at
      ) <>(MDocument.tupled, MDocument.unapply)
  }

  val tDocument = TableQuery[TableDocument]

  class TableDocumentComment(tag: Tag) extends Table[MDocumentComment](tag, "m_document_comment") with MyTable[SuperId, MDocumentComment] {
    val id = column[SuperId]("id", O.PrimaryKey, SUPER_ID_TYPE)
    val document_id = column[SuperId]("document_id")
    val quote_id = column[SuperId]("quote_id", O.Nullable)
    val user_id = column[Long]("user_id", O.Nullable)
    val anonymous_email = column[String]("anonymous_email", O.Nullable)
    val anonymous_nick = column[String]("anonymous_nick", O.Nullable)
    val content = column[String]("content", O.DBType(driver.DB_TYPE_TEXT))
    val created_at = column[DateTime]("created_at")

    val _fkDocumentId = foreignKey(tableName + "_fk_document", document_id, tDocument)(_.id)
    val _fkUser = foreignKey(tableName + "_fk_user", user_id, tUser)(_.id)

    def * = (id.?, document_id, quote_id.?, user_id.?, anonymous_email.?, anonymous_nick.?, content, created_at
      ) <>(MDocumentComment.tupled, MDocumentComment.unapply)
  }

  val tDocumentComment = TableQuery[TableDocumentComment]

  lazy val ddls = tTag.ddl ++
    tUser.ddl ++ tUserPassword.ddl ++
    tImage.ddl ++ tDocument.ddl ++ tDocumentComment.ddl

  // TODO 改为从data source获取
  val db = Database.forURL(conf.url, conf.username, conf.password, driver = MyJdbcDriver.is.DRIVER_CLASS)

}
