package com.agh.wikidrill.api

import com.agh.wikidrill.DefaultJsonSupport
import com.agh.wikidrill.models.QuizModel
import org.scalatra.ScalatraServlet

/**
  * Created by sirius on 24.06.17.
  */
class QuizServlet extends ScalatraServlet with DefaultJsonSupport {

  get("/") {
    for (el <- QuizModel.list()) yield el.toString
  }

}
