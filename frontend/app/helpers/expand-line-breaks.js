import Ember from 'ember';

export function expandLineBreaks(params) {
  return new Ember.Handlebars.SafeString(params[0].replace(/\n/g, '<br>'));
}

export default Ember.Helper.helper(expandLineBreaks);
