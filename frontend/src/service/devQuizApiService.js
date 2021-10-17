import axios from 'axios'

export function getQuestions() {
  return axios
    .get('/api/question')
    .then(response => response.data)
    .catch(err => console.error(err))
}

export function addQuestion(newQuestion) {
  return axios
    .post('/api/question', newQuestion)
    .then(response => response.data)
    .catch(console.error)
}
export function validateAnswers(validationObject){
  return axios
      .post('/api/question/validate', validationObject)
      .then(response => response.data)
      .catch(console.error)
}
