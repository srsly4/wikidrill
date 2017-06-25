package com.agh.wikidrill.api

import com.agh.wikidrill.DefaultJsonSupport
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
    QuizModel.getById(new ObjectId(params("id"))).toString
  }

  //create new
  post("/") {
    try {
      val inserted = parsedBody.extract[QuizModel]
      var result: Option[ActionResult] = None
      for (question <- inserted.questions) {
        if (question.revisions.length > 1)
          result = Some(BadRequest("Inserted questions can not have more than 1 revisions"))
        question.revisions.head.created = Some(DateTime.now())
      }

      result match {
        case Some(response) => response
        case None => {
          QuizModel.saveNew(inserted)
          Ok("Inserted")
        }
      }
    }
    catch {
      case MappingException(msg, _) => BadRequest("Invalid format: " + msg)
      case ex: JsonParseException => BadRequest("Invalid json format")
    }
  }

}
