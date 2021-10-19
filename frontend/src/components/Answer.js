import * as React from 'react'
import styled from 'styled-components'

function Answer({ answer, questionId, sendChosenId}) {

    return (
    <AnswerContainer>
      <input type="radio" name={questionId} onChange={()=>sendChosenId(answer.id)}/>
      <h4>{answer.answerText}</h4>

    </AnswerContainer>
  )
}
export default Answer

const AnswerContainer = styled.section`
  display: flex;
  align-items: center;
  gap: 5px;
`
