package com.agh.wikidrill.models

import com.agh.wikidrill.{DatabaseProvider, DatabaseSupport}
import com.mongodb.casbah.commons.{MongoDBObject, TypeImports}
import org.bson.types.ObjectId
import salat._
import salat.global._
import salat.annotations._
import org.joda.time.DateTime
import com.mongodb.casbah.Imports._

case class QuizModel private (@Key("_id") id: ObjectId, name: String,
                              @Ignore var questions: List[QuestionModel] = Nil)
  extends DatabaseSupport {
  require(!name.isEmpty)

  def updateQuestions(): Unit = {
    val res = QuizModel.questionCollection.find(MongoDBObject("quiz_id" -> id))
    questions = Nil
    for (el <- res) questions = questions.::(grater[QuestionModel].asObject(el))
  }

  def insertQuestions(): Unit = {
    for (question <- questions) {
      QuizModel.questionCollection.insert(grater[QuestionModel].asDBObject(question))
    }
  }
}

object QuizModel extends DatabaseSupport {
  private val quizCollection = database("quiz")
  private val questionCollection = database("questions")

  def list(): Iterable[QuizModel] = {
    quizCollection.find()
    for (el <- quizCollection) yield grater[QuizModel].asObject(el)
  }

  def getById(objectId: ObjectId): QuizModel = {
    val retrieved = quizCollection.findOne(MongoDBObject("_id" -> objectId))
    val model = grater[QuizModel].asObject(retrieved.get)
    model.updateQuestions()
    model
  }

  def saveNew(quizModel: QuizModel): Unit = {
    quizCollection.insert(grater[QuizModel].asDBObject(quizModel))
    quizModel.insertQuestions()
  }

}
