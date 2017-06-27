package com.agh.wikidrill.adapters

import com.agh.wikidrill.models.QuestionRevisionModel
import org.bson.types.ObjectId
import org.joda.time.DateTime

case class QuestionRevisionInsertAdapter(text: String, answers: List[AnswerAdapter]) {
  def createModel(question_id: ObjectId): QuestionRevisionModel =
    QuestionRevisionModel(question_id, text, DateTime.now, answers.map( el => el.createModel))
}
