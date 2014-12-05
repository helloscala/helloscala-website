package com.helloscala.platform.util

import org.json4s.DefaultFormats
import org.json4s.ext.JodaTimeSerializers
import org.json4s.mongo.{ObjectIdSerializer, UUIDSerializer, DateSerializer, PatternSerializer}

object Y extends yangbajing.util.Y {
  private val mongoSerializers = Seq(new ObjectIdSerializer, new UUIDSerializer, new DateSerializer,
    new PatternSerializer)


  implicit val json4sDefaultFormats = DefaultFormats ++ JodaTimeSerializers.all ++ mongoSerializers
}
