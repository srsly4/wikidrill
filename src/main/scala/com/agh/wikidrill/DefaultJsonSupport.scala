package com.agh.wikidrill

import com.mongodb.casbah.commons.conversions.scala.JodaDateTimeSerializer
import org.bson.types.ObjectId
import org.json4s.{DefaultFormats, Formats, _}
import org.scalatra.json.JacksonJsonSupport
import org.json4s.mongo.ObjectIdSerializer
import org.json4s.ext.DateTimeSerializer
import org.json4s.JsonAST.{JString, JValue}

/**
  * Created by sirius on 24.06.17.
  */

class CustomObjectIdSerializer extends Serializer[ObjectId] {
  private val ObjectIdClass = classOf[ObjectId]

  def deserialize(implicit format: Formats): PartialFunction[(TypeInfo, JValue), ObjectId] = {
    case (TypeInfo(ObjectIdClass, _), json) => json match {
      case JString(s) if ObjectId.isValid(s) => new ObjectId(s)
    }
  }

  def serialize(implicit formats: Formats): PartialFunction[Any, JValue] = {
    case x: ObjectId => JString(x.toString)
  }
}

trait DefaultJsonSupport extends JacksonJsonSupport {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats + new CustomObjectIdSerializer + DateTimeSerializer

  before() {
    contentType = formats("json")
  }
}
