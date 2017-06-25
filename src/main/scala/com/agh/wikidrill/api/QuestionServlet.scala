package com.agh.wikidrill.api

import com.agh.wikidrill.adapters.QuestionRevisionInsertAdapter
import com.agh.wikidrill.{DefaultJsonSupport, NotFoundException}
import com.agh.wikidrill.models.QuestionModel
import org.bson.types.ObjectId
import org.scalatra.{BadRequest, NotFound, Ok, ScalatraServlet}

/**
  * Created by sirius on 26.06.17.
  */
class QuestionServlet extends ScalatraServlet with DefaultJsonSupport {

  get("/:id"){
    try {
      val id = new ObjectId(params("id"))
      QuestionModel.getById(id)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Unknown error!")
    }
  }

  get("/latest/:id"){
    try {
      QuestionModel.getLatestRevision(new ObjectId(params("id")))
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Unknown error!")
    }
  }

  post("/add-revision/:id"){
    try {
      val questionId = new ObjectId(params("id"))
      val adapter = parsedBody.extract[QuestionRevisionInsertAdapter]
      QuestionModel.createRevision(questionId, adapter.createModel())
      Ok()
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Unknown error!")
    }
  }


}
