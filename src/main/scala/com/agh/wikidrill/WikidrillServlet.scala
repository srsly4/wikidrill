package com.agh.wikidrill

import org.scalatra._

class WikidrillServlet extends WikidrillStack {

  get("/") {
    <html>
      <body>
        <h1>Hello, world!</h1>
        <p>What the hell?</p>
        Say <a href="hello-scalate">hello to Scalate</a>.
      </body>
    </html>
  }

  get("/quizzes") {
    Ok("Quizes list")
  }

}
