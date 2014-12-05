package com.helloscala.platform.common

/**
 * 账户
 * Created by Yang Jing on 2014-11-24.
 */
sealed abstract class AccountType(val value: Int, val name: String) extends MyType

object AccountTypes extends MyTypes[AccountType] {

  case object AllAccount extends AccountType(0, "所有账户")

  case object AdminAccount extends AccountType(1, "管理员账户")

  case object GuestAccount extends AccountType(2, "来宾账户")

  case object SignInAccount extends AccountType(3, "登录账户")

  override def extraction(value: Int): AccountType = value match {
    case AllAccount.VALUE => AllAccount
    case AdminAccount.VALUE => AdminAccount
    case GuestAccount.VALUE => GuestAccount
    case SignInAccount.VALUE => SignInAccount
  }

  override def extraction(name: String): AccountType = name match {
    case AllAccount.NAME => AllAccount
    case AdminAccount.NAME => AdminAccount
    case GuestAccount.NAME => GuestAccount
    case SignInAccount.NAME => SignInAccount
  }

}