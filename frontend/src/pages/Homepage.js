import * as React from 'react'
import styled from 'styled-components'
import QuestionOverview from "../components/QuestionOverview";

function Homepage({ questions }) {

  const sendChosenId = () => {}

  return (
    <QuestionsContainer>
      {questions.map(question => (
        <QuestionOverview question={question} key={question.id} sendChosenId={sendChosenId} />
      ))}
    </QuestionsContainer>
  )
}
export default Homepage

const QuestionsContainer = styled.section`
  display: flex;
  gap: 15px;
  justify-content: center;
  flex-wrap: wrap;
  background-color: #424B54;
  padding: 50px;
`
