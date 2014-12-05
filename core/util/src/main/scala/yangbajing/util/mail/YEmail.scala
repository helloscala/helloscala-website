package yangbajing.util.mail

import javax.activation.DataSource
import java.net.URL

import scala.xml.NodeSeq

import org.apache.commons.mail.{EmailAttachment, HtmlEmail}

case class YEmail(to: Seq[(String, Option[String], Option[String])],
                  from: (String, Option[String], Option[String]),
                  subject: String,
                  server: YEmailServer) {

  val email = new HtmlEmail
  email.setHostName(server.smtp_host)
  email.setSmtpPort(server.smtp_port)
  email.setAuthentication(server.username, server.password)
  email.setSSLOnConnect(server.realSslOn)

  to.foreach {
    case (m, None, None) =>
      email.addTo(m)

    case (m, Some(n), None) =>
      email.addTo(m, n)

    case (m, Some(n), Some(c)) =>
      email.addTo(m, n, c)

    case _ =>
  }

  from match {
    case (m, None, None) =>
      email setFrom m

    case (m, Some(n), None) =>
      email setFrom(m, n)

    case (m, Some(n), Some(c)) =>
      email setFrom(m, n, c)

    case _ =>
  }

  email.setCharset("UTF-8")

  email.setSubject(subject)

  def send() =
    email.send()

  def setMsg(msg: String) = {
    email.setMsg(msg)
    this
  }

  def setHtmlMsg(msg: NodeSeq) = {
    email.setHtmlMsg(msg.toString())
    this
  }

  def setTextMsg(msg: String) = {
    email.setTextMsg(msg)
    this
  }

  def attach(attachment: EmailAttachment) = {
    email.attach(attachment)
    this
  }

  def attach(dataSource: DataSource, name: String, desc: String, disposition: String) = {
    email.attach(dataSource, name, desc, disposition)
    this
  }

  def attach(url: URL, name: String, desc: String, disposition: String) = {
    email.attach(url, name, desc, disposition)
    this
  }

}
