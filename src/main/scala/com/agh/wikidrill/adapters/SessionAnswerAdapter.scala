package com.agh.wikidrill.adapters

import com.agh.wikidrill.models.SessionModel
import org.bson.types.ObjectId

/**
  * Created by sirius on 27.06.17.
  */
case class SessionAnswerAdapter(answer_type: String) {
  def createModel(sessionId: ObjectId): SessionModel = {
    val session = SessionModel.getById(sessionId)

    assert(session.current_index < session.questions.length)
    assert(session.answers.keySet.contains(answer_type))
    session.answers = session.answers.updated(answer_type, session.answers(answer_type)+1)
    session.current_index += 1
    session
  }
}
