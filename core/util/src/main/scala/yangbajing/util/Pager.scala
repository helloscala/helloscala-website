package yangbajing.util

import scala.xml.{Elem, NodeSeq}

trait Pager[T] {
  /**
   * 每页显示多少数据
   */
  def limit: Int

  def curDocument(): Option[String]

  def prevDocument(): Option[String]

  def nextDocument(): Option[String]

  /**
   * 数据的总行数
   */
  def total: Long

  /**
   * 当前页的数据
   */
  def page: List[T]

  def pager(uri: String, ulClass: String = "pager"): Elem = {

    val prev =
      prevDocument() match {
        case Some(v) =>
          <li>
            <a href={"%s&document=%s&comparison=$lt&limit=%d" format(uri, v, limit)}>
              &larr;
              上一页</a>
          </li>

        case None =>
          NodeSeq.Empty
      }


    val next =
      nextDocument() match {
        case Some(v) =>
          <li>
            <a href={"%s&document=%s&comparison=$gt&limit=%d" format(uri, v, limit)}>下一页
              &rarr;
            </a>
          </li>

        case None =>
          NodeSeq.Empty
      }

    <ul class={ulClass}>
      {prev}{next}
    </ul>
  }

}

trait PagerSort {
  def sortby: String

  /**
   * @return desc, asc
   */
  def sorting: String
}
