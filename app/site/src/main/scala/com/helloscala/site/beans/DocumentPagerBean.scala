package com.helloscala.site.beans

import com.helloscala.platform.model.entity.MDocument
import com.helloscala.platform.util.Params

case class DocumentPagerBean(items: java.util.List[MDocument], count: Long, params: Params) {

}
