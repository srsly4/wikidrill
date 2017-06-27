import DS from 'ember-data';

export default DS.Model.extend({
  text: DS.attr("string"),
  created: DS.attr(),
  answers: DS.attr()
});
