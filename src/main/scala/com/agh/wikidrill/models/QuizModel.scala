package com.agh.wikidrill.models

import com.agh.wikidrill.{DatabaseProvider, DatabaseSupport}
import com.mongodb.casbah.commons.{MongoDBObject, TypeImports}
import org.bson.types.ObjectId
import salat._
import salat.global._
import salat.annotations._
import org.joda.time.DateTime
import com.mongodb.casbah.Imports._

case class QuizModel private (name: String, questions: List[QuestionModel])
  extends DatabaseSupport {
  require(!name.isEmpty)
  require(questions.nonEmpty)

}

object QuizModel extends DatabaseSupport {
  private val quizCollection = database("quiz")

  def list(): Iterable[QuizModel] = {

    com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()
    quizCollection.find()
    for (el <- quizCollection) yield grater[QuizModel].asObject(el)
  }

  def getById(objectId: ObjectId): QuizModel = {
    val retrieved = quizCollection.findOne(MongoDBObject("_id" -> objectId))
    grater[QuizModel].asObject(retrieved.get)
  }

}
