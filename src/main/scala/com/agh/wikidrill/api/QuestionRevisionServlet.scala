package com.agh.wikidrill.api

import com.agh.wikidrill.adapters.QuestionRevisionInsertAdapter
import com.agh.wikidrill.{DefaultJsonSupport, NotFoundException}
import com.agh.wikidrill.models.QuestionModel
import org.bson.types.ObjectId
import org.scalatra.{BadRequest, NotFound, Ok, ScalatraServlet}

/**
  * Created by sirius on 27.06.17.
  */
class QuestionRevisionServlet extends ScalatraServlet with DefaultJsonSupport {

  get("/:id"){
    try {
      val question_id = new ObjectId(params("id"))
      val revision = QuestionModel.getLatestRevision(question_id)
      revision.id = question_id
      Map("question-revision" -> revision)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case ex: Exception => BadRequest("Exception: " + ex.getMessage)
      case _: Throwable => BadRequest("Unknown error!")
    }
  }

  put("/:id"){
    try {
      val questionId = new ObjectId(params("id"))
      val adapter = parsedBody.children.head.extract[QuestionRevisionInsertAdapter]
      QuestionModel.createRevision(questionId, adapter.createModel(questionId))
      val revision = QuestionModel.getLatestRevision(questionId)
      revision.id = questionId
      Map("question-revision" -> revision)
    }
    catch {
      case NotFoundException(msg, _) => NotFound(msg)
      case _: Throwable => BadRequest("Unknown error!")
    }
  }

}
