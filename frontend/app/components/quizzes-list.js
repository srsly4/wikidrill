import Ember from 'ember';

export default Ember.Component.extend({
  actions: {
    delete_item(model){
      if (confirm("Czy jesteś pewien, że chcesz usunąć ten quiz?"))
        model.destroyRecord();
    }
  }
});
