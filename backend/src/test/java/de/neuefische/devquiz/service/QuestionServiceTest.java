package de.neuefische.devquiz.service;

import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.tokens.AliasToken;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    private final QuestionRepo questionRepo = mock(QuestionRepo.class);
    private final QuestionService questionService = new QuestionService(questionRepo);

    @Test
    @DisplayName("returns a list of all existing questions")
    void listQuestions() {
        //GIVEN
        List<Question> expected = List.of(new Question("1", "Frage?", List.of()), new Question("2", "Zweite Frage?", List.of()));
        when(questionRepo.findAll()).thenReturn(expected);

        //WHEN
        List<Question> actual = questionService.getAllQuestions();

        //THEN
        Assertions.assertIterableEquals(expected, actual);
        verify(questionRepo).findAll();
    }

    @Test
    @DisplayName("Should throw a exception when the given id is not in the db")
    void testGet_IdNotFound() {
        //GIVEN
        when(questionRepo.findById("209")).thenReturn(Optional.empty());

        //WHEN
        Assertions.assertThrows(NoSuchElementException.class, () -> questionService.get("209"));

        verify(questionRepo).findById("209");
    }

    @Test
    void addQuestion() {
        //GIVEN
        Question expected = new Question("999", "Frage?", List.of());
        when(questionRepo.save(expected)).thenReturn(expected);

        //WHEN
        Question actual = questionService.addQuestion(expected);

        //THEN
        assertEquals(expected, actual);
        verify(questionRepo).save(expected);
    }
}