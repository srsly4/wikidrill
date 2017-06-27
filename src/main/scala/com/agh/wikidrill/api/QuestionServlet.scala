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
      val question = QuestionModel.getById(id)
      question.sortRevisions()
      Map("question" -> question)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Unknown error!")
    }
  }



}
