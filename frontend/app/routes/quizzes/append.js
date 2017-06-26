import Ember from 'ember';

export default Ember.Route.extend({
  actions: {
    send_raw(quiz, raw_text) {
      let context = this;
      quiz.append_raw({"raw": raw_text}).then((session) => {
        context.set("raw_text", "");
        quiz.reload().then((session) => {
          context.transitionTo("quizzes.view", quiz.id);
        });
      });
    }
  },
  model(params){
    return this.get('store').findRecord('quiz', params.quiz_id)
  }
});
