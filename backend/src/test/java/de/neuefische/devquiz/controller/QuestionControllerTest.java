package de.neuefische.devquiz.controller;


import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.model.ValidationInfo;
import de.neuefische.devquiz.repo.QuestionRepo;

import de.neuefische.devquiz.security.model.AppUser;
import de.neuefische.devquiz.security.repo.AppUserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.http.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DevQuizControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepo userRepo;

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
        ResponseEntity<Question[]> responseEntity = testRestTemplate.exchange("/api/question", HttpMethod.GET,
                new HttpEntity<>(getHttpHeadersWithJWT()), Question[].class);
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
        ResponseEntity<Question> responseEntity = testRestTemplate.exchange("/api/question/302", HttpMethod.GET,
                new HttpEntity<>(getHttpHeadersWithJWT()), Question.class);
        // THEN
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody(), is(new Question("302", "Question with ID '302'", List.of())));
    }

    @Test
    @DisplayName("Should add a new question item to the db")
    void testAddQuestion() {
        HttpHeaders headers = getHttpHeadersWithJWT();

        // GIVEN
        Question questionToAdd = new Question("22", "This is a question", List.of());

        // WHEN
        ResponseEntity<Question> postResponseEntity = testRestTemplate.exchange("/api/question/", HttpMethod.POST, new HttpEntity<>(questionToAdd, headers), Question.class);
        Question actual = postResponseEntity.getBody();

        // THEN
        assertThat(postResponseEntity.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(actual);
        assertThat(actual, is(new Question("22", "This is a question", List.of())));

        // THEN - check via GET
        String actualId = actual.getId();
        ResponseEntity<Question> getResponse = testRestTemplate.exchange("/api/question/" + actualId, HttpMethod.GET, new HttpEntity<>(headers), Question.class);
        Question persistedQuestion = getResponse.getBody();

        assertNotNull(persistedQuestion);
        assertThat(persistedQuestion.getId(), is(questionToAdd.getId()));
        assertThat(persistedQuestion.getQuestionText(), is(questionToAdd.getQuestionText()));

    }

    private HttpHeaders getHttpHeadersWithJWT() {
        userRepo.save(AppUser.builder().username("test_username").password(passwordEncoder.encode("some-password")).build());
        AppUser loginData = new AppUser("test_username", "some-password");
        ResponseEntity<String> response = testRestTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(response.getBody());
        return headers;
    }
}