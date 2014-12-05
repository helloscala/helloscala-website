package yangbajing.common

trait TRespMessage extends Product {
  def status: TStatusMessage

  def data: TDataMessage
}
