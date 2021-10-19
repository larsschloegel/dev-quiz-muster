package de.neuefische.devquiz.repo;

import de.neuefische.devquiz.model.Question;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends PagingAndSortingRepository<Question, String> {

    List<Question> findAll();


}
