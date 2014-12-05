package yangbajing.common

case class HostName(schema: String, siteId: String, name: String, domain: String, port: Int, contextPath: String) {
  val descPort = port match {
    case 80 => ""
    case 443 => ""
    case n => ":" + n
  }

  val levelSite: String = url(siteId, "")

  val mainSite: String = mainUrl("")

  /**
   *
   * @param id
   * @param path 必须以 / 开头，并相对于域名根目录。
   * @return
   */
  def url(id: String, path: String = ""): String =
    s"${schema}://${id}.${name}.${domain + descPort + contextPath + path}"

  def mainUrl(path: String): String =
    s"${schema}://${name}.${domain + descPort + contextPath + path}"

}
