import Ember from 'ember';

export default Ember.Route.extend({
  actions: {
    add(quiz_name){
      let context = this;
      let quiz = this.get('store').createRecord('quiz', {
        name: quiz_name,
        questions: []
      });
      quiz.save();
      context.transitionTo("quizzes");
    }
  }
});
