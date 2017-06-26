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
});

export default Router;
