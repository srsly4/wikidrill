import Ember from 'ember';

export default Ember.Route.extend({
  actions: {
    save(revision){
      let context = this;
      revision.save().then(()=>{
        context.transitionTo("question.view", revision.id)
      })
    }
  },
  model(params){
    return this.get('store').findRecord('question-revision', params.question_id);
  }
});
