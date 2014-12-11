package com.helloscala.platform.util

import spray.http.StatusCodes
import yangbajing.common.TStatusMessage

/**
 * 返回消息
 * Created by Yang Jing on 2014-10-31.
 */
case class StatusMsg(code: Int, msg: String) extends TStatusMessage {
  def error(msg: String) = StatusMsg(code, msg)
}

object StatusMsgs {
  val Ok = value(0, "")
  val Error = value(-1, "其它错误")

  val EmailInvalid = value(-1001, "邮箱地址无效")
  val MobileInvalid = value(-1002, "移动电话号无效")
  val IdInvalid = value(-1003, "ID无效")
  val SQLError = value(-1004, "SQL数据库错误")
  val RequestTimeout = value(-1005, "请求超时")
  val QueryParamsMissing = value(-1006, "请求参数缺失: ")
  val SessionTokenNotExists = value(-1006, "Token不存在")
  val SessionTokenInvalid = value(-1007, "Token无效")
  val Rejection = value(-1008, "路由拒绝")
  val HttlReanderFailure = value(-1009, "Httl模板渲染错误")

  val CreateFailure = value(-11401, "创建资源失败")
  val RemoveFailure = value(-11402, "删除资源失败")
  val UpdateFailure = value(-11403, "修改资源失败")
  val DataNotFound = value(-11404, "资源未找到")
  val UploadDataEmpty = value(-11405, "上传资源为空")
  val InvalidUploadRequest = value(-11406, "不是有效的文件上传请求")
  val UploadImageFormatInvalid = value(-11407, "图片格式不正确，请上传：png, jpeg, gif图片。")

  val AccountInvalid = value(-20001, "账号无效，请输入邮箱/手机号/用户名")
  val AccountNotExists = value(-20002, "账号不存在")
  val AccountIdInvalid = value(-20003, "账号ID无效")
  val AccountEditFailure = value(-20004, "编辑账号失败")
  val AccountAuthError = value(-20005, "账号密码验证错误")

  def success(msg: String) = StatusMsg(Ok.code, msg)

  def queryParamError(msg: String) = StatusMsg(QueryParamsMissing.code, QueryParamsMissing.msg + msg)

  def sqlError(msg: String) = StatusMsg(SQLError.code, msg)

  def httlReaderError(msg: String) = StatusMsg(HttlReanderFailure.code, msg)

  def error(msg: String) = StatusMsg(Error.code, msg)

  def rejection(msg: String) = StatusMsg(Rejection.code, msg)


  @inline private def value(code: Int, msg: String) = StatusMsg(if (code < 0) code else -code, msg)
}
