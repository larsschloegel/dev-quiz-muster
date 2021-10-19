package de.neuefische.devquiz.service;

import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.AnswerValidation;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PlayService {
    private final QuestionRepo questionRepo;

    @Autowired
    public PlayService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public Question getRandomQuestion() {
        List<Question> allQuestions = questionRepo.findAll();
        if (allQuestions.size() == 0) {
            throw new NoSuchElementException("No questions in database");
        }

        Collections.shuffle(allQuestions);

        return allQuestions.get(0);
    }

    public boolean checkAnswer(AnswerValidation answerValidation) {
        //Deconstruct AnswerValidation-Objekt
        Question actualQuestion = answerValidation.getQuestion();
        String chosenId = answerValidation.getChosenId();

        return actualQuestion.getAnswers().stream()
                .anyMatch(answer -> (answer.getCorrect()) && (answer.getId().equals(chosenId)));
    }
}
