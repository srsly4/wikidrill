import com.agh.wikidrill._
import org.scalatra._
import javax.servlet.ServletContext

import com.agh.wikidrill.api.{QuestionServlet, QuizServlet}

class ScalatraBootstrap extends LifeCycle {
  override def init(context: ServletContext) {
    context.mount(new WikidrillServlet, "/*")
    context.mount(new QuizServlet, "/quiz/*")
    context.mount(new QuestionServlet, "/question/*")
  }
}
