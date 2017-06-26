package com.agh.wikidrill.api

import com.agh.wikidrill.adapters.{SessionAnswerAdapter, SessionCreateAdapter}
import com.agh.wikidrill.{DefaultJsonSupport, NotFoundException}
import com.agh.wikidrill.models.SessionModel
import org.bson.types.ObjectId
import org.json4s.MappingException
import org.scalatra.{BadRequest, NotFound, ScalatraServlet}

/**
  * Created by sirius on 27.06.17.
  */
class SessionServlet extends ScalatraServlet with DefaultJsonSupport {

  //get current session status with question content
  get("/:id"){
    try {
      val session = SessionModel.getById(new ObjectId(params("id")))
      session.loadCurrentQuestion()
      Map("session" -> session)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Unknown error has arisen")
    }
  }

  //create session with given quiz_id as body argument
  post("/"){
    try {
      val adapter = parsedBody.children.head.extract[SessionCreateAdapter]
      val model = adapter.createModel()
      SessionModel.saveNew(model)
      model.loadCurrentQuestion()
      model
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case MappingException(msg, _) => BadRequest("Invalid format: " + msg)
      case _: Throwable => BadRequest("Unknown error has arisen")
    }
  }

  //answer to current status' questions
  post("/:id/answer"){
    try {
      val adapter = parsedBody.extract[SessionAnswerAdapter]
      val session = adapter.createModel(new ObjectId(params("id")))
      session.update()
      session.loadCurrentQuestion()
      Map("session" -> session)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case MappingException(msg, _) => BadRequest("Invalid format: " + msg)
      case _: AssertionError => BadRequest("Answer not permitted")
      case _: Throwable => BadRequest("Unknown error has arisen")
    }
  }



}
