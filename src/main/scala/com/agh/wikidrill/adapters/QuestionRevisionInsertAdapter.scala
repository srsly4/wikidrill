package com.agh.wikidrill.adapters

import com.agh.wikidrill.models.QuestionRevisionModel
import org.joda.time.DateTime

case class QuestionRevisionInsertAdapter(text: String, answers: List[AnswerAdapter]) {
  def createModel(): QuestionRevisionModel =
    QuestionRevisionModel(text, DateTime.now, answers.map( el => el.createModel))
}
