package com.agh.wikidrill.models

import com.agh.wikidrill.DatabaseSupport
import org.bson.types.ObjectId
import salat.annotations._
import org.joda.time.DateTime

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

}
