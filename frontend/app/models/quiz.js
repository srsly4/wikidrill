import DS from 'ember-data';
import { memberAction } from 'ember-api-actions';

export default DS.Model.extend({
  // id: DS.attr(),
  name: DS.attr("string"),
  questions: DS.attr(),

  append_raw: memberAction({
    path: 'append-raw',
    type: 'post',
    urlType: 'findRecord'
  })
});
