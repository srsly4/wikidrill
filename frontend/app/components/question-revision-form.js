import Ember from 'ember';

export default Ember.Component.extend({
  actions: {
    remove_answer(answer, revision){
      revision.get("answers").removeObject(answer);
    },
    add_answer(revision){
      revision.get("answers").pushObject(
        Ember.Object.create({
          text: "",
          truth: false
        })
      );
    }

  }
});
