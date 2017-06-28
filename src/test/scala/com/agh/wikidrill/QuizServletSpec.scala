package com.agh.wikidrill

import com.agh.wikidrill.api.QuizServlet
import org.scalatra.test.specs2._
import org.specs2.specification.core.SpecStructure

/**
  * Created by sirius on 28.06.17.
  */
class QuizServletSpec extends ScalatraSpec {
  override def is: SpecStructure = s2"""
    GET / on QuizServlet
      return status 200
    """

  addServlet(classOf[QuizServlet], "/*")

  def getRoot200 = get("/") {
    status must_== 200
  }
}
