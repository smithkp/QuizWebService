package engine.dao;

import engine.model.CompletedQuiz;
import engine.model.CompletedQuizResponse;
import engine.model.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface CompletionRepository extends PagingAndSortingRepository<CompletedQuiz, Long> {

    Page<CompletedQuizResponse> findByAccountId(Long id, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO Completions (id,completedAt,author) VALUES(:quizId, :completedAt, :author )", nativeQuery = true)
    void insertCompletedQuiz(@Param("quizId") Long quizId, @Param("completedAt") LocalDateTime completionTime, @Param("author")String author);
}
