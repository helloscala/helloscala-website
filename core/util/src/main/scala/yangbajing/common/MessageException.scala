package yangbajing.common

case class MessageException(msg: TStatusMessage) extends RuntimeException(msg.code + " : " + msg.msg) {
  override def toString: String = getClass.getSimpleName + "(" + msg.code + ", " + msg.msg + ")"
}
