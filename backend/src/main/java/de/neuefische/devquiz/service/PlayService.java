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
    private List<Question> answeredQuestions = List.of();
    int allQuestionsIndexForQuestionForFrontend = 0;

    @Autowired
    public PlayService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public Question getQuestion() {
        List<Question> allQuestions = questionRepo.findAll();
        if (allQuestions.size() == 0) {
            throw new NoSuchElementException("No questions in database");
        }

        Collections.shuffle(allQuestions);

        for (Question answeredQuestion : answeredQuestions) {
            if (answeredQuestion.equals(allQuestions.get(allQuestionsIndexForQuestionForFrontend))) {
                allQuestionsIndexForQuestionForFrontend++;
            } else {
                return allQuestions.get(allQuestionsIndexForQuestionForFrontend);
            }

            if (allQuestionsIndexForQuestionForFrontend >= allQuestions.size()) {       //size-1?
                allQuestionsIndexForQuestionForFrontend = 0;
            }

            if (answeredQuestions.size() >= allQuestions.size()) {
                answeredQuestions.clear();
            }
        }
        return allQuestions.get(0);
    }

    public boolean checkAnswer(AnswerValidation answerValidation) {
        //Deconstruct FrontendTry-Objekt
        Question actualQuestion = answerValidation.getQuestion();
        String chosenId = answerValidation.getChosenId();
        for (Answer answer : actualQuestion.getAnswers()) {
            if ((answer.getCorrect()) && (answer.getId().equals(chosenId))) {
                return true;
            }
        }
        return false;
    }
}
