import Ember from 'ember';

export default Ember.Route.extend({
  actions: {
    delete_question(question_id){
      if (!confirm("Czy jesteś pewny?"))
        return;
      let store = this.get('store');
      let context = this;
      store.findRecord('question', question_id).then(function(q){
        q.destroyRecord();
        context.currentModel.reload();
      })
    }
  },
  model(params){
    return this.get('store').findRecord('quiz', params.quiz_id, { reload: true })
  }
});
