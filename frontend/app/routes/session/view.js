import Ember from 'ember';

export default Ember.Route.extend({
  checked_response: false,
  selected: Ember.computed('selected_arr.@each', function(){

  }),
  selected_arr: {},
  actions: {
    check_answers(session){
      let question = session.get("current_question");
      let answers = question.answers;
      let correct = 0;
      let wrong = 0;
      let totalTruth = 0;

      let truthMatches = 0;

      let context = this;
      answers.forEach((answer, index) => {
        if (answer.truth) totalTruth++;
        let curr = false;
        if (typeof answer.checked !== 'undefined')
          curr = answer.checked;

        if (answer.truth) //no mark
          Ember.set(answer, 'correct', true);

        if (answer.truth === curr) {
          correct++;
          if (answer.truth) {
            truthMatches++;
            // answer.correct = true;
            context.selected[index] = true;
          }
        }
        else {
          if (curr) //wrong mark
          {
            wrong++;
            Ember.set(answer, 'wrong', true);
          }
        }
      });

      let response = 'bad';
      if (correct === answers.length){
        response = 'correct';
      }
      else if (wrong === 0 && truthMatches > 0){
        response = 'partiall'
      }

      console.log(response);
      this.checked_response = response;
      Ember.set(session, 'checked_response', response);
      Ember.set(question, 'answers', answers);
    },
    next_question(session){
      if (this.checked_response === false){
        alert("Najpierw sprawdź odpowiedzi!");
      }
      else {
        session.answer({
          answer_type: this.checked_response
        }).then(()=>{
          session.reload();
          Ember.set(session, 'checked_response', false);
        })
      }
    }
  },
  init(){
    this.set('selected', [])
  },
  model(params){
    return this.get('store').findRecord('session', params.session_id, { reload: true });
  }
});
