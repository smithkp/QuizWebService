package engine.dao;

import engine.model.Quiz;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Qualifier("quizzes")
@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    Quiz findById(long id);
    Page<Quiz> findAll(Pageable pageable);
    void deleteById(long id);
}
