package engine.service;

import engine.dao.CompletionRepository;
import engine.dao.UserAccountRepository;
import engine.model.CompletedQuizResponse;
import engine.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    @Autowired
    private CompletionRepository completionRepo;
    @Autowired
    private UserAccountRepository accountRepo;

    public Page<CompletedQuizResponse> getQuizCompletions(int page, int size, String sortBy, String username) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        UserAccount user = accountRepo.findByUsername(username);
        Page<CompletedQuizResponse> pagedResult = completionRepo.findByAccountId(user.getId(), pageable);

        return pagedResult;
    }
}
