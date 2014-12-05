package yangbajing.common

trait TStatusMessage extends Product {
  def code: Int

  def msg: String

  def isSuccess = code == 0

  def isError = !isSuccess
}
