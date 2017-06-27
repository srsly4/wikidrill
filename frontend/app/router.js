import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('quizzes', function() {
    this.route('view', { path: "/:quiz_id" });
    this.route('append', { path: "/:quiz_id/append" });
    this.route('add');
  });

  this.route('session', function() {
    this.route('view', { path: "/:session_id"});
  });

  this.route('question', function() {
    this.route('view', { path: "/:question_id"});
  });

  this.route('question-revision', function() {
    this.route('edit', { path: "/:question_id"});
  });
});

export default Router;
