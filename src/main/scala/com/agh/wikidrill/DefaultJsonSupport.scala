package com.agh.wikidrill

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.json.JacksonJsonSupport

/**
  * Created by sirius on 24.06.17.
  */
trait DefaultJsonSupport extends JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  before() {
    contentType = formats("json")
  }
}
