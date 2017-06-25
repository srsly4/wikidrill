package com.agh.wikidrill

import com.mongodb.casbah.commons.conversions.scala.JodaDateTimeSerializer
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport
import org.json4s.mongo.ObjectIdSerializer
import org.json4s.ext.DateTimeSerializer

/**
  * Created by sirius on 24.06.17.
  */
trait DefaultJsonSupport extends JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats + new ObjectIdSerializer + DateTimeSerializer

  before() {
    contentType = formats("json")
  }
}
