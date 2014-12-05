package yangbajing.util.mail

case class YEmailServer(
                         id: String,
                         smtp_host: String,
                         smtp_port: Int,
                         username: String,
                         password: String,
                         email: Option[String] = None,
                         ssl_on: Option[Boolean] = None,

                         // smtp, pop3
                         protocol: Option[String] = None) {

  def realEmail = email.getOrElse(username)

  def realSslOn = ssl_on.getOrElse(true)

  def realProtocal = protocol.getOrElse("smtp")
}
