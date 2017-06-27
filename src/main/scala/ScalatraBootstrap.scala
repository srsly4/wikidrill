import com.agh.wikidrill._
import org.scalatra._
import javax.servlet.ServletContext

import com.agh.wikidrill.api.{QuestionRevisionServlet, QuestionServlet, QuizServlet, SessionServlet}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new WikidrillServlet, "/*")
    context.mount(new QuizServlet, "/quizzes/*")
    context.mount(new QuestionServlet, "/questions/*")
    context.mount(new QuestionRevisionServlet, "/questionRevisions/*")
    context.mount(new SessionServlet, "/sessions/*")
  }
}
