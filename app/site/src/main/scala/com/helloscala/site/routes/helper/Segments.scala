package com.helloscala.site.routes.helper

import com.helloscala.platform.common.SuperId
import org.bson.types.ObjectId
import shapeless.HNil
import spray.http.Uri.Path
import spray.routing.PathMatcher.{Matched, Unmatched}
import spray.routing.PathMatcher1

object ObjectIdSegment extends PathMatcher1[ObjectId] {
  def apply(path: Path) = path match {
    case Path.Segment(segment, tail) if ObjectId.isValid(segment) => Matched(tail, new ObjectId(segment) :: HNil)
    case _ => Unmatched
  }
}

object SuperIdSegment extends PathMatcher1[SuperId] {
  def apply(path: Path) = path match {
    case Path.Segment(segment, tail) if ObjectId.isValid(segment) => Matched(tail, SuperId(segment) :: HNil)
    case _ => Unmatched
  }
}
