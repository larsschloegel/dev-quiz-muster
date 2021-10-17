import * as React from 'react'
import Answer from './Answer'
import styled from 'styled-components'
import {useState} from "react";
import {validateAnswers} from "../service/devQuizApiService";

function Question({question}) {

    const [answerIdState, setAnswerIdState] = useState("")

    const handleChoice = answerId => setAnswerIdState(answerId);

    function validateAnswer() {
        const validateObject = {
            questionID: question.id,
            answerID: answerIdState
        }
        validateAnswers(validateObject).then(result => {
            if (JSON.stringify(result) === JSON.stringify(validateObject)) {
                console.log("Stimmt")
            } else {
                console.log("Stimmt nicht")
            }
        })
    }

    return (

        <QuestionContainer>
            <h3>{question.questionText}</h3>
            <AnswerContainer>
                {question.answers.map(answer => (
                    <Answer answer={answer} key={answer.id} questionId={question.id} handleChoice={handleChoice}/>
                ))}
            </AnswerContainer>
            <CheckButton onClick={validateAnswer}>Check Answer</CheckButton>
        </QuestionContainer>
    )
}

export default Question

const QuestionContainer = styled.section`
  width: 400px;
  border: 1px solid #009fb7;
  border-radius: 20px;
  padding: 20px;
  background-color: #EAF6FF;
  font-family: 'Montserrat', sans-serif;;
`

const AnswerContainer = styled.section`
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 0 0;
  grid-template-areas:
    '. .'
    '. .';
`
const CheckButton = styled.button`
  box-shadow: inset 0 1px 0 0 #ffffff;
  background-color: #757780;
  border-radius: 6px;
  border: 1px solid #dcdcdc;
  display: inline-block;
  cursor: pointer;
  color: white;
  font-family: 'Montserrat', sans-serif;
  font-size: 15px;
  font-weight: bold;
  padding: 6px 24px;
  text-decoration: none;

  &:hover {
    background: linear-gradient(to bottom, #dfdfdf 5%, #ededed 100%);
    background-color: #dfdfdf;
    color: #757780;
  }

  &:active {
    position: relative;
    top: 1px;
  }
`
