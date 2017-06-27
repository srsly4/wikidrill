import Ember from 'ember';

export default Ember.Component.extend({
  actions: {
    delete_item(model){
      if (confirm("Czy jesteś pewien, że chcesz usunąć ten quiz?"))
        model.destroyRecord();
    },
    start_session(model){
      let store = Ember.inject.service('store');
      let session = store.createRecord('session', {
        quiz_id: model.id
      });
      let context = this;
      session.save().then(()=>{
        context.transitionTo("session.view", session.id);
      })

    }
  }
});
