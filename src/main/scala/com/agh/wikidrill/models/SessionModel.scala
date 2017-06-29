package com.agh.wikidrill.models

import com.agh.wikidrill.{DatabaseSupport, NotFoundException}
import com.agh.wikidrill.models.QuizModel.quizCollection
import com.mongodb.casbah.commons.{MongoDBObject, TypeImports}
import org.bson.types.ObjectId
import salat._
import salat.global._
import salat.annotations._
import org.joda.time.DateTime
import com.mongodb.casbah.Imports._
import scala.util.Random.{shuffle}

case class SessionModel(@Key("_id") id: ObjectId, quiz_id: ObjectId,
                       creation_date: DateTime,
                       var answers: Map[String, Int],
                       var current_index: Int, questions: List[ObjectId],
                       @Ignore var current_question: Option[QuestionRevisionModel] = None){

  def update(): Unit = {
    SessionModel.sessionCollection.update(MongoDBObject("_id" -> this.id),
      grater[SessionModel].asDBObject(this))
  }

  def loadCurrentQuestion(): Unit = {
    current_question = if (current_index < questions.length)
      Some(SessionModel.getQuestionFromSessionId(this.id))
    else None
  }

  def completed: Boolean = {
    current_index >= questions.length
  }

  def shuffleAnswers(): Unit = {
    if (current_question.isEmpty)
      this.loadCurrentQuestion()
    this.current_question.get.answers = shuffle(this.current_question.get.answers)
  }

}

object SessionModel extends DatabaseSupport {
  private val sessionCollection = database("sessions")

  def saveNew(sessionModel: SessionModel): Unit = {
    sessionCollection.insert(grater[SessionModel].asDBObject(sessionModel))
  }

  def getById(sessionId: ObjectId): SessionModel = {
    val retrieved = sessionCollection.findOne(MongoDBObject("_id" -> sessionId))
    if (retrieved.isEmpty)
      throw NotFoundException("Session by given id could not have been found")
    val model = grater[SessionModel].asObject(retrieved.get)
    model
  }

  def getQuestionFromSessionId(sessionId: ObjectId): QuestionRevisionModel = {
    val session = getById(sessionId)
    try {
      val questionId = session.questions(session.current_index)
      val question = QuestionModel.getById(questionId)
      question.sortRevisions()
      val revision = question.revisions.head
      revision.id = questionId

      revision
    }
    catch {
      case ex : Throwable =>
        throw NotFoundException("Could not retrieve correct question for given session", ex)
    }
  }
}
