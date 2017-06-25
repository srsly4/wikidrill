package com.agh.wikidrill.models

import com.agh.wikidrill.{DatabaseSupport, NotFoundException}
import com.mongodb.casbah.commons.MongoDBObject
import org.bson.types.ObjectId
import salat._
import salat.global._
import salat.annotations._
import org.joda.time.DateTime
import com.mongodb.casbah.Imports._

case class AnswerModel(text: String, truth: Boolean){
  require(!text.isEmpty)
}

case class QuestionRevisionModel(text: String, var created: DateTime,
                                 answers: List[AnswerModel]){
  require(!text.isEmpty)
  require(answers.nonEmpty)
}

case class QuestionModel(@Key("_id") id: ObjectId, quiz_id: ObjectId, revisions: List[QuestionRevisionModel]){
  require(revisions.nonEmpty)
}


object QuestionModel extends DatabaseSupport {
  private val questionCollection = database("questions")

  def getById(id: ObjectId): QuestionModel = {
    val ret = questionCollection.findOne(MongoDBObject("_id" -> id))
    if (ret.isEmpty)
      throw NotFoundException("Question not found")
    grater[QuestionModel].asObject(ret.get)
  }

  def createRevision(questionId: ObjectId, revision: QuestionRevisionModel): Unit = {
    getById(questionId) //assure question exists

    questionCollection.update(MongoDBObject("_id" -> questionId),
      MongoDBObject("$push" -> MongoDBObject("revisions" -> grater[QuestionRevisionModel].asDBObject(revision))))

  }

  def saveNew(question: QuestionModel): Unit = {
    questionCollection.insert(grater[QuestionModel].asDBObject(question))
  }

  def getLatestRevision(questionId: ObjectId): QuestionRevisionModel = {
    val question = getById(questionId)
    question.revisions
      .sortWith((left, right) => left.created.getMillis > right.created.getMillis)
      .head
  }

  def delete(id: ObjectId): Unit = {
    if (questionCollection.findAndRemove(MongoDBObject("_id" -> id)).isEmpty)
      throw NotFoundException("Question not found")
  }
}
