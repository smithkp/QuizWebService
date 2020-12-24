package engine.service;

import engine.dao.CompletionRepository;
import engine.dao.UserAccountRepository;
import engine.exception.custom.ForbiddenException;
import engine.exception.custom.QuizNotFoundException;
import engine.model.CompletedQuiz;
import engine.model.Quiz;
import engine.dao.QuizRepository;
import engine.model.AnswerResponse;
import engine.model.QuizResult;
import engine.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class QuizService {
    @Qualifier("quizzes")
    @Autowired
    private QuizRepository quizRepo;
    @Qualifier("accounts")
    @Autowired
    private UserAccountRepository accountRepo;
    @Autowired
    private CompletionRepository completionRepo;

    public QuizService() {
    }

    public void saveQuiz(Quiz quiz, HttpServletRequest request) {
        UserAccount account = accountRepo.findByUsername(request.getUserPrincipal().getName());
        quiz.setAccount(account);
        quizRepo.save(quiz);
    }

    public Page<Quiz> getAllQuizzes(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Quiz> pagedResult = quizRepo.findAll(pageable);

        return pagedResult;
    }

    public Quiz getQuiz(long id) {
        Optional<Quiz> quiz = Optional.ofNullable(quizRepo.findById(id));
        if (quiz.isEmpty()) {
            throw new QuizNotFoundException("Quiz not found for id " + id);
        }
        else {
            return quiz.get();
        }
    }

    /*
     * This method checks correctness of answer by comparing quiz object answer to answer sent by user.
     * answers are converted to Integer[] and sorted.
     */
    public QuizResult solveQuiz(long id, AnswerResponse answer, String username) {
        Integer[] quizAnswer = new Integer[quizRepo.findById(id).getAnswer().size()];
        quizAnswer = quizRepo.findById(id).getAnswer().toArray(quizAnswer);

        Integer[] attempt = new Integer[answer.getAnswers().size()];
        attempt = answer.getAnswers().toArray(attempt);

        Arrays.sort(quizAnswer);
        Arrays.sort(attempt);

        if (Arrays.equals(quizAnswer, attempt)) {
            UserAccount user = accountRepo.findByUsername(username);
            completionRepo.save(new CompletedQuiz(id,LocalDateTime.now(),user));
            return new QuizResult(true, "Correct!");
        } else {
            return new QuizResult(false, "Try Again!");
        }
    }

    public void deleteById(long id, String username) {
        Optional<Quiz> quizToBeDeleted = Optional.ofNullable(quizRepo.findById(id));
        UserAccount account = accountRepo.findByUsername(username);
        if (quizToBeDeleted.isEmpty()) {
            throw new QuizNotFoundException("Quiz with id:"+id + " does not exist");
        }
        else if (!quizToBeDeleted.get().getAccount().equals(account)) {
            throw new ForbiddenException("Cannot delete quiz that you did not author");
        }
        else {
            quizRepo.deleteById(id);
        }
    }
}
