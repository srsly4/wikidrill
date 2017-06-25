package com.agh.wikidrill.api

import com.agh.wikidrill.DefaultJsonSupport
import com.agh.wikidrill.adapters.QuizInsertAdapter
import com.agh.wikidrill.models.QuizModel
import com.fasterxml.jackson.core.JsonParseException
import org.json4s.MappingException
import org.scalatra.{ActionResult, BadRequest, Ok, ScalatraServlet}
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
    val retrv = QuizModel.getById(new ObjectId(params("id")))
    retrv
  }

  //create new
  post("/") {
    try {
      val body = request.body
      val pbody = parsedBody
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
    }
  }

}
