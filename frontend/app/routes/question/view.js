import Ember from 'ember';

export default Ember.Route.extend({
  model(params){
    return this.get("store").findRecord('question', params.question_id);
  }
});
