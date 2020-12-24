package engine.controller;

import engine.model.AnswerResponse;
import engine.model.Quiz;
import engine.model.QuizResult;
import engine.service.CompletionService;
import engine.service.QuizService;
import engine.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;


@RestController
public class QuizController {

    @Autowired
    private  QuizService quizService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private CompletionService completionService;


    public QuizController() {

    }

    @RequestMapping(value = "/api/quizzes/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Quiz getQuiz(@PathVariable long id) {
        return quizService.getQuiz(id);
    }

    @GetMapping(value = "/api/quizzes")
    @ResponseBody
    public Page<Quiz> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        Page<Quiz> quizList = quizService.getAllQuizzes(page, size, sortBy);
        return quizList;
    }


    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    public Quiz createQuiz(@Valid @RequestBody Quiz quiz, HttpServletRequest request) {
        quizService.saveQuiz(quiz, request);
        return quiz;
    }


    @PostMapping(value = "/api/quizzes/{id}/solve", consumes = "application/json")
    @ResponseBody
    public QuizResult solveQuiz(@PathVariable long id, @RequestBody AnswerResponse answer, Principal principal) {
        return quizService.solveQuiz(id, answer, principal.getName());
    }

    @DeleteMapping(value = "/api/quizzes/{id}")
    public ResponseEntity<Object> deleteQuiz(@PathVariable long id, Principal principal) {
        quizService.deleteById(id, principal.getName());
        HashMap<String, String> deleteMessage = new HashMap<>();
        deleteMessage.put("message", "successfully deleted");
        return new ResponseEntity<Object>(deleteMessage, HttpStatus.NO_CONTENT);
    }

}
