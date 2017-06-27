import DS from 'ember-data';
import { memberAction } from 'ember-api-actions';

export default DS.Model.extend({
  quiz_id: DS.attr("string"),
  creation_date: DS.attr(),
  questions: DS.attr(),
  answers: DS.attr(),
  current_index: DS.attr(),
  current_question: DS.attr(),

  completed: Ember.computed('current_index', 'questions', function(){
    let current_index = this.get("current_index");
    let questions = this.get("questions");

    return current_index >= questions.length;
  }),

  question_number: Ember.computed('current_index', function(){
    let current_index = this.get("current_index");
    return current_index + 1;
  }),

  questions_length: Ember.computed('questions', function(){
    let questions = this.get("questions");
    return questions.length;
  }),

  answer: memberAction({
    path: 'answer',
    type: 'post',
    urlType: 'findRecord'
  })
});
