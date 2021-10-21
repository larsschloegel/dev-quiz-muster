package de.neuefische.devquiz.security.controller;

import de.neuefische.devquiz.security.model.ActualUser;
import de.neuefische.devquiz.security.model.AppUser;
import de.neuefische.devquiz.security.repo.AppUserRepo;
import de.neuefische.devquiz.security.service.JWTUtilService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "neuefische.devquiz.jwt.secret=super-secret")
class UserControllerTest {

    @Autowired
    private AppUserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtilService jwtUtilService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getLoggedInUserWithValidToken() {
        //GIVEN
        HttpHeaders headers = getHttpHeadersWithJWT();
        //WHEN
        ResponseEntity<ActualUser> response = restTemplate.exchange("/api/user/me", HttpMethod.GET, new HttpEntity<>(headers), ActualUser.class);
        //THEN

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertEquals(response.getBody().getUsername(), "test_username");
    }

    @Test
    void getLoggedInUserWithInvalidToken() {
        //GIVEN
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("ey.dasistkeinrichtigertoken.sanfosih");
        //WHEN
        ResponseEntity<ActualUser> response = restTemplate.exchange("/api/user/me", HttpMethod.GET, new HttpEntity<>(headers), ActualUser.class);
        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    void getLoggedInUserWithExpiredToken() {
        //GIVEN
        HttpHeaders headers = new HttpHeaders();

        //ReflectionTestUtils.setField(jwtUtilService, "duration", 1);
        headers.setBearerAuth(jwtUtilService.createToken(new HashMap<>(), "test_username"));

        headers.setBearerAuth("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJleHAiOjE2MzQ3Mzc1NTQsImlhdCI6MTYzNDczNzQ5NH0.hRz2tvtXjLbof29HFGWTcGGk8VxliVoD17Yhl-uEPdw");
        //WHEN
        ResponseEntity<ActualUser> response = restTemplate.exchange("/api/user/me", HttpMethod.GET, new HttpEntity<>(headers), ActualUser.class);
        //THEN

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private HttpHeaders getHttpHeadersWithJWT() {
        userRepo.save(AppUser.builder().username("test_username").password(passwordEncoder.encode("some-password")).build());
        AppUser loginData = new AppUser("test_username", "some-password");
        ResponseEntity<String> response = restTemplate.postForEntity("/auth/login", loginData, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(response.getBody());
        return headers;
    }

}