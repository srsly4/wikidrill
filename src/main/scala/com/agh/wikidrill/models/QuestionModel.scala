package com.agh.wikidrill.models

import com.agh.wikidrill.DatabaseSupport
import org.joda.time.DateTime

case class AnswerModel(text: String, truth: Boolean){
  require(!text.isEmpty)
}

case class QuestionRevision(text: String, created: DateTime, answers: List[AnswerModel]){
  require(!text.isEmpty)
  require(answers.nonEmpty)
}

case class QuestionModel(revisions: List[QuestionRevision]){
  require(revisions.nonEmpty)
}


object QuestionModel extends DatabaseSupport {

}
