package de.neuefische.devquiz.service;
import de.neuefische.devquiz.model.Answer;
import de.neuefische.devquiz.model.ValidationInfo;
import de.neuefische.devquiz.model.Question;
import de.neuefische.devquiz.repo.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepo questionRepo;

    @Autowired
    public QuestionService(QuestionRepo questionRepo) {
        this.questionRepo = questionRepo;
    }

    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public Question addQuestion(Question newQuestion){
        return questionRepo.save(newQuestion);
    }

    public Question get(String id) {
        Optional<Question> optionalQuestion = questionRepo.findById(id);

        if (optionalQuestion.isEmpty()) {
            throw new NoSuchElementException("Question with id:" + id + " not found!");
        }

        return optionalQuestion.get();
    }

    public ValidationInfo validateQuestion(ValidationInfo InputQuestionIdAndAnswerId) {
        Optional<Question> questionToCheck = questionRepo.findById(InputQuestionIdAndAnswerId.getQuestionID());
        List<Answer> allAnswer = questionToCheck.get().getAnswers();
        ValidationInfo validationAnswer= new ValidationInfo(InputQuestionIdAndAnswerId.getQuestionID(), null);
        for (Answer answer : allAnswer){
            if(answer.getIsCorrect()){
                validationAnswer.setAnswerID(answer.getId());
            }
        }
        return validationAnswer;
    }

}
