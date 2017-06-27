package com.agh.wikidrill.adapters

import com.agh.wikidrill.models.{QuizModel, SessionModel}
import org.bson.types.ObjectId
import org.joda.time.DateTime

import scala.collection.mutable.ListBuffer
import scala.util.Random.shuffle

case class SessionCreateAdapter(quiz_id: ObjectId) {

  def createModel(): SessionModel = {
    val quiz = QuizModel.getById(quiz_id)
    val question_indexes: ListBuffer[ObjectId] = ListBuffer()

    for (question <- quiz.questions)
      question_indexes.append(question.id)

    //create new session with empty statistics
    SessionModel(new ObjectId(), quiz_id, DateTime.now(),
      Map("correct" -> 0, "partiall" -> 0, "bad" -> 0), 0,
      shuffle(question_indexes.to[List]))
  }
}
