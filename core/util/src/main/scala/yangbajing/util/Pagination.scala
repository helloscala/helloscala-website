package yangbajing.util

import scala.xml.Elem

import yangbajing.common.MathHelpers.yMinSwap

trait Pagination[T] {
  /**
   * 当前页码（从1开始计数）
   */
  def curPage: Int

  /**
   * 每页显示多少数据
   */
  def limit: Int

  /**
   * 数据的总行数
   */
  def total: Int

  /**
   * 当前页的数据
   */
  def page: List[T]

  /**
   * 总页数
   */
  def numPages: Int = ((total + limit - 1) / limit).toInt

  // (total / limit).toInt + (if (total % limit > 0) 1 else 0)

  // 数据行实际偏移（用于数据库查询）
  def offset = (curPage - 1) * limit

  /**
   * 每页显示页码数
   */
  val pagingLimit: Int = 10

  /**
   * 页码实际偏移
   */
  def pagingOffset: Int = (curPage + pagingLimit) / pagingLimit

  def pagingEnd = yMinSwap(pagingOffset + pagingLimit - 1, numPages)

  def pageUri(pageKey: String, limitKey: String, uri: String, cp: Int): String =
    s"$uri?$pageKey=$cp&$limitKey=$limit"

  def pagination(uri: String,
                 ulClass: String = "pagination pagination-sm pull-right",
                 curPageClass: String = "active",
                 pageKey: String = "cur_page",
                 limitKey: String = "limit"): Elem = {

    val _first =
      if (numPages > pagingLimit && curPage != 1)
        <li>
          <a href={pageUri(pageKey, limitKey, uri, 1)}>首页</a>
        </li>

    val _prev =
      if (curPage > 1)
        <li>
          <a href={pageUri(pageKey, limitKey, uri, curPage - 1)}>上一页</a>
        </li>

    val _next =
      if (curPage <= numPages && curPage != numPages)
        <li>
          <a href={pageUri(pageKey, limitKey, uri, curPage + 1)}>下一页</a>
        </li>

    val _last =
      if (numPages > pagingLimit && curPage != numPages)
        <li>
          <a href={pageUri(pageKey, limitKey, uri, numPages)}>末页</a>
        </li>

    val _pages =
      (pagingOffset to pagingEnd).map(idx =>
        if (idx == curPage)
          <li class={curPageClass}>
            <a>
              {idx}
            </a>
          </li>
        else
          <li>
            <a href={pageUri(pageKey, limitKey, uri, idx)}>
              {idx}
            </a>
          </li>)

    <ul class={ulClass}>
      {_first}{_prev}{_pages}{_next}{_last}
    </ul>
  }

}

trait PaginationSort {
  def sortBy: String

  /**
   * @return desc, asc
   */
  def sorting: String
}
