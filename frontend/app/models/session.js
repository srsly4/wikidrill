import Ember from 'ember';
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

  progress: Ember.computed('questions', 'current_index', function(){
    return Math.round(100*this.get("current_index")/this.get("questions_length"))
  }),

  result: Ember.computed('answers.correct', 'current_index', function(){
    let curr_ndx = this.get("current_index");
    if (curr_ndx === 0) return 0
    return Math.round(100*this.get('answers').correct/curr_ndx);
  }),

  answer: memberAction({
    path: 'answer',
    type: 'post',
    urlType: 'findRecord'
  })
});
