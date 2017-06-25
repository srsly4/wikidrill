package com.agh.wikidrill.adapters
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.agh.wikidrill.models.{AnswerModel, QuestionModel, QuestionRevisionModel, QuizModel}

/**
  * Created by sirius on 25.06.17.
  */
case class QuizInsertAdapter(name: String, questions: List[QuestionInsertAdapter]) {
  require(!name.isEmpty)
  require(questions.nonEmpty)

  def createModel: QuizModel = {
    val new_id = new ObjectId()
    val questions_model = questions.map { (el) => {el.createModel(new_id)} }
    QuizModel(new_id, name, questions_model)
  }
}

case class QuestionInsertAdapter(text: String, answers: List[AnswerInsertAdapter]){
  require(!text.isEmpty)
  require(answers.nonEmpty)

  def createModel(quiz_id: ObjectId): QuestionModel = {
    val answers_model = answers.map { (el) => { el.createModel } }
    val rev_model = QuestionRevisionModel(text, DateTime.now(), answers_model)
    QuestionModel(new ObjectId(), quiz_id, rev_model :: Nil)
  }
}

case class AnswerInsertAdapter(text: String, truth: Boolean){
  require(!text.isEmpty)

  def createModel: AnswerModel = {
    val model = AnswerModel(text, truth)
    model
  }
}