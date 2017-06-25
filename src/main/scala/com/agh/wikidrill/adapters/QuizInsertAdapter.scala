package com.agh.wikidrill.adapters
import org.bson.types.ObjectId
import org.joda.time.DateTime
import com.agh.wikidrill.models.{AnswerModel, QuestionModel, QuestionRevisionModel, QuizModel}

import scala.util.matching.Regex

/**
  * Created by sirius on 25.06.17.
  */
case class QuizInsertAdapter(name: String, questions: List[QuestionInsertAdapter]) {
  require(!name.isEmpty)
  //we should have a possibility to create empty quiz
  //require(questions.nonEmpty)

  def createModel: QuizModel = {
    val new_id = new ObjectId()
    val questions_model = questions.map { (el) => {el.createModel(new_id)} }
    QuizModel(new_id, name, questions_model)
  }
}

case class QuestionInsertAdapter(text: String, answers: List[AnswerAdapter]){
  require(!text.isEmpty)
  require(answers.nonEmpty)

  def createModel(quiz_id: ObjectId): QuestionModel = {
    val answers_model = answers.map { (el) => { el.createModel } }
    val rev_model = QuestionRevisionModel(text, DateTime.now(), answers_model)
    QuestionModel(new ObjectId(), quiz_id, rev_model :: Nil)
  }
}

case class RawQuestionsAddAdapter(raw: String){

  def createModel(quiz_id: ObjectId): List[QuestionModel] = {
    var questions: List[QuestionInsertAdapter] = Nil

    val answerTruePattern = "^>>>[a-zA-Z]\\).+$".r
    val answerFalsePattern = "^[a-zA-Z]\\).+$".r

    val rawLines = "\r\n|\r|\n".r.split(raw) ++ Array("") // assurance for last question saving
    var currText = ""
    var currAnswers : List[AnswerAdapter] = List()
    for (line <- rawLines){
      if (answerTruePattern.findFirstIn(line).isDefined){
        //truth answer
        val parsed = "^>>>[a-zA-Z]\\)".r.replaceFirstIn(line, "").trim()
        currAnswers = AnswerAdapter(parsed, truth = true) :: currAnswers
      }
      else if (answerFalsePattern.findFirstIn(line).isDefined){
        //false answer
        val parsed = "^[a-zA-Z]\\)".r.replaceFirstIn(line, "").trim()
        currAnswers = AnswerAdapter(parsed, truth = false) :: currAnswers
      }
      else {
        //part of question

        //if there were any answers before, save the question
        if (currAnswers.nonEmpty){
          questions = questions :+ QuestionInsertAdapter(currText, currAnswers)
          currText = ""
          currAnswers = Nil
        }
        currText += line
      }
    }

    for (q <- questions) yield q.createModel(quiz_id)
  }
}

case class AnswerAdapter(text: String, truth: Boolean){
  require(!text.isEmpty)

  def createModel: AnswerModel = {
    val model = AnswerModel(text, truth)
    model
  }
}