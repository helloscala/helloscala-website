package com.helloscala.platform.util

import spray.http._

object HttpResponses {

  final val RequestTimeoutResponse = HttpResponse(
    StatusCodes.OK,
    Y.json4sToString(StatusMsgs.RequestTimeout)(Y.json4sDefaultFormats),
    List(HttpHeaders.`Content-Type`(ContentType(MediaTypes.`application/json`, HttpCharsets.`UTF-8`))))

}
