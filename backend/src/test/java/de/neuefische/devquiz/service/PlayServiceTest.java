package de.neuefische.devquiz.service;

import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.AnswerValidation;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayServiceTest {

    private final QuestionRepo questionRepo = mock(QuestionRepo.class);
    private final PlayService playService = new PlayService(questionRepo);

    @Test
    @DisplayName("returns a (random) Question, here the only one")
    void getQuestion() {
        //GIVEN
        Question expected = new Question("1", "Frage?", List.of());
        List<Question> withOneExpected = List.of(expected);
        when(questionRepo.findAll()).thenReturn(withOneExpected);

        //WHEN
        Question actual = playService.getRandomQuestion();

        //THEN
        assertEquals(expected, actual);
        verify(questionRepo).findAll();
    }

    @Test
    @DisplayName("checks answer of question - here correct")
    void checkAnswerTrue() {
        //GIVEN
        Answer answer1 = new Answer();
        answer1.setAnswerText("True answer");
        answer1.setCorrect(true);
        answer1.setId(UUID.randomUUID().toString());

        Answer answer2 = new Answer();
        answer2.setAnswerText("False answer");
        answer2.setCorrect(false);
        answer2.setId(UUID.randomUUID().toString());

        Question question = new Question("1", "Frage?", List.of(answer1, answer2));
        String chosenId = answer1.getId();
        AnswerValidation answerValidation = new AnswerValidation(question, chosenId);

        //WHEN
        boolean actual = playService.checkAnswer(answerValidation);

        //THEN
        assertTrue(actual);
    }

    @Test
    @DisplayName("checks answer of question - here false")
    void checkAnswerFalse() {
        //GIVEN
        Answer answer1 = new Answer();
        answer1.setAnswerText("True answer");
        answer1.setCorrect(true);
        answer1.setId(UUID.randomUUID().toString());

        Answer answer2 = new Answer();
        answer2.setAnswerText("False answer");
        answer2.setCorrect(false);
        answer2.setId(UUID.randomUUID().toString());

        Question question = new Question("1", "Frage?", List.of(answer1, answer2));
        String chosenId = answer2.getId();
        AnswerValidation answerValidation = new AnswerValidation(question, chosenId);

        //WHEN
        boolean actual = playService.checkAnswer(answerValidation);

        //THEN
        assertFalse(actual);
    }


}