package de.neuefische.devquiz.controller;


import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.model.ValidationInfo;
import de.neuefische.devquiz.repo.QuestionRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DevQuizControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private QuestionRepo questionRepo;

    @BeforeEach
    public void clearDb() {
        questionRepo.deleteAll();
    }

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Should return a list with all questions from db")
    void testListQuestion() {
        // GIVEN

        questionRepo.save(new Question("1", "Question with ID '1'", List.of()));
        questionRepo.save(new Question("2", "Question with ID '2'", List.of()));
        questionRepo.save(new Question("3", "Question with ID '3'", List.of()));
        // WHEN
        ResponseEntity<Question[]> responseEntity = testRestTemplate.getForEntity("/api/question", Question[].class);
        // THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), arrayContainingInAnyOrder(
                new Question("1", "Question with ID '1'", List.of()),
                new Question("2", "Question with ID '2'", List.of()),
                new Question("3", "Question with ID '3'", List.of())
        ));

    }

    @Test
    @DisplayName("Should return a question object with the given id")
    void testGet() {
        // GIVEN
        Question question = new Question("302", "Question with ID '302'", List.of());

        questionRepo.save(question);
        // WHEN
        ResponseEntity<Question> responseEntity = testRestTemplate.getForEntity("/api/question/" + question.getId(), Question.class);
        // THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(new Question("302", "Question with ID '302'", List.of())));

    }

    @Test
    @DisplayName("Should add a new question item to the db")
    void testAddQuestion() {
        // GIVEN
        Question questionToAdd = new Question("22", "This is a question", List.of());


        // WHEN
        ResponseEntity<Question> postResponseEntity = testRestTemplate.postForEntity("/api/question/", questionToAdd, Question.class);
        Question actual = postResponseEntity.getBody();

        // THEN
        assertThat(postResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(actual);
        assertThat(actual, is(new Question("22", "This is a question", List.of())));

        // THEN - check via GET
        ResponseEntity<Question> getResponse = testRestTemplate.getForEntity("/api/question/" + questionToAdd.getId(), Question.class);
        Question persistedQuestion = getResponse.getBody();

        assertNotNull(persistedQuestion);
        assertThat(persistedQuestion.getId(), is(questionToAdd.getId()));
        assertThat(persistedQuestion.getQuestionText(), is(questionToAdd.getQuestionText()));

    }

    @Test
    @DisplayName("Should return the right answer")
    void testValidateQuestion() {
        // GIVEN
        Question questionToValidate = new Question("302", "Question with ID '302'", List.of(
                new Answer("bc0ff0d7-9a23-4708-b545-07a6c14f1d24", "a", true)
        ));
        ResponseEntity<Question> postResponseEntity = testRestTemplate.postForEntity("/api" +
                "/question", questionToValidate, Question.class);
        ValidationInfo validationInfo = new ValidationInfo("302" ,"bc0ff0d7-9a23-4708-b545" +
                "-07a6c14f1d24");
        // WHEN
        ResponseEntity<ValidationInfo> postResponseValidationInfo =
                testRestTemplate.postForEntity("/api/question/validate", validationInfo,
                        ValidationInfo.class);
        ValidationInfo actual = postResponseValidationInfo.getBody();

        // THEN
        assertThat(postResponseValidationInfo.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(actual);
        assertThat(actual, is(validationInfo));
    }

}