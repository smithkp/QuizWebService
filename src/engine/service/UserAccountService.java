package engine.service;

import engine.dao.CompletionRepository;
import engine.dao.UserAccountRepository;
import engine.exception.custom.ApiRequestException;
import engine.model.CompletedQuizResponse;
import engine.model.UserAccount;
import engine.model.UserAccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService implements UserDetailsService {

    private UserAccountRepository repo;

    @Autowired
    private CompletionRepository completionRepo;

    public UserAccountService(@Qualifier("accounts")UserAccountRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        if (repo.findByUsername(username) != null){
            UserAccount account = repo.findByUsername(username);
            UserAccountDetails details = new UserAccountDetails(account);
            return details;
        }
        else {
            throw new ApiRequestException("Username not found : " + username);
        }
    }

    public void saveUserAccount(UserAccount userAccount) throws RuntimeException{

            if (repo.existsByUsername(userAccount.getUsername())) {
                throw new ApiRequestException("username " + userAccount.getUsername() + " is already in use");
            }

            String hashedPass = new BCryptPasswordEncoder().encode(userAccount.getPassword());
            userAccount.setPassword(hashedPass);
            repo.save(userAccount);


   }

   public List<UserAccount> getAllUsers() {
        return repo.findAll();
   }

   public Page<CompletedQuizResponse> getQuizCompletions(int page, int size, String sortBy, String username) {
       Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
       UserAccount user = repo.findByUsername(username);
       Page<CompletedQuizResponse> pagedResult = completionRepo.findByAccountId(user.getId(), pageable);

       return pagedResult;
   }
}
