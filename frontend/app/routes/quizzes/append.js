import Ember from 'ember';

export default Ember.Route.extend({
  actions: {
    send_raw(quiz, raw_text) {
      quiz.append_raw({"raw": raw_text});
      this.set("raw_text", "");
      quiz.reload();
      this.transitionTo("quizzes.view", quiz.id);
    }
  },
  model(params){
    return this.get('store').findRecord('quiz', params.quiz_id)
  }
});
