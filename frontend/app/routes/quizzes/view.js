import Ember from 'ember';

export default Ember.Route.extend({
  model(params){
    return this.get('store').findRecord('quiz', params.quiz_id, { reload: true })
  }
});
