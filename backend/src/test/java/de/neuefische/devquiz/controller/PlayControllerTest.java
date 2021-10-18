package de.neuefische.devquiz.controller;

import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.AnswerValidation;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlayControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private QuestionRepo questionRepo;

    @BeforeEach
    public void clearDb() {
        questionRepo.deleteAll();
    }

    @Test
    @DisplayName("Should return a random question from db (here only one question in db)")
    void testGetQuestion() {

        //GIVEN
        questionRepo.save(new Question("1", "Question with ID '1'", List.of()));

        //WHEN
        ResponseEntity<Question> responseEntity = testRestTemplate.getForEntity("/api/question/quiz", Question.class);

        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(new Question("1", "Question with ID '1'", List.of())));
    }

    @Test
    @DisplayName("Should return a true")
    void testCheckAnswerTrue() {

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

        questionRepo.save(question);
        AnswerValidation answerValidation = new AnswerValidation(question, chosenId);

        //WHEN
        ResponseEntity<Boolean> responseEntity = testRestTemplate.postForEntity("/api/question/quiz", answerValidation, Boolean.class );

        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(Boolean.TRUE));


    }

    @Test
    @DisplayName("Should return a false")
    void testCheckAnswerFalse() {

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

        questionRepo.save(question);
        AnswerValidation answerValidation = new AnswerValidation(question, chosenId);

        //WHEN
        ResponseEntity<Boolean> responseEntity = testRestTemplate.postForEntity("/api/question/quiz", answerValidation, Boolean.class );

        //THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(Boolean.FALSE));
    }
}