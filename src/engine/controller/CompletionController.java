package engine.controller;

import engine.model.CompletedQuizResponse;
import engine.service.CompletionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CompletionController {
    @Autowired
    private CompletionService completionService;

    @GetMapping(value = "/api/quizzes/completed")
    public Page<CompletedQuizResponse> getCompletions(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "completedAt") String sortBy,
            Principal principal
    ) {
        return completionService.getQuizCompletions(page,size,sortBy,principal.getName());
    }
}
