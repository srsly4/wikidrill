package com.agh.wikidrill.api

import com.agh.wikidrill.{DefaultJsonSupport, NotFoundException}
import com.agh.wikidrill.adapters.{QuizInsertAdapter, RawQuestionsAddAdapter}
import com.agh.wikidrill.models.{QuestionModel, QuizModel}
import com.fasterxml.jackson.core.JsonParseException
import org.json4s.MappingException
import org.scalatra._
import org.bson.types.ObjectId
import org.joda.time.DateTime
import org.json4s.ext.JodaTimeSerializers

/**
  * Created by sirius on 24.06.17.
  */
class QuizServlet extends ScalatraServlet with DefaultJsonSupport {

  // list of available quizzes
  get("/") {
    for (el <- QuizModel.list()) yield el.toString
  }

  //view one
  get("/:id") {
    try {
      val retrv = QuizModel.getById(new ObjectId(params("id")))
      retrv
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _ : Throwable => BadRequest("Something's gone terribly wrong!")
    }
  }

  //create new
  post("/") {
    try {
      val adapter = parsedBody.extract[QuizInsertAdapter]
      val result: Option[ActionResult] = None
      val inserted = adapter.createModel

      result match {
        case Some(response) => response
        case None => {
          QuizModel.saveNew(inserted)
          Ok(inserted)
        }
      }
    }
    catch {
      case MappingException(msg, _) => BadRequest("Invalid format: " + msg)
      case ex: JsonParseException => BadRequest("Invalid json format")
      case _: Throwable => BadRequest("Something's gone terribly wrong!")
    }
  }

  //delete one
  delete("/:id") {
    try {
      val quizId = new ObjectId(params("id"))
      QuizModel.delete(quizId)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Something's gone terribly wrong!")
    }
  }

  //append raw questions
  post("/append-raw/:id") {
    try {
      val quizId = new ObjectId(params("id"))
      val quiz = QuizModel.getById(quizId)

      val adapter = parsedBody.extract[RawQuestionsAddAdapter]
      val loadedQuestions = adapter.createModel(quizId)
      for (question <- loadedQuestions){
        QuestionModel.saveNew(question)
      }

      Map(
        "added" -> loadedQuestions.length
      )
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: java.lang.IllegalArgumentException => BadRequest("Invalid format")
      case _: Throwable => BadRequest("Something's gone terribly wrong!")
    }
  }

}
