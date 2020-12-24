package engine.dao;

import engine.model.UserAccount;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Qualifier("accounts")
@Repository
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
    List<UserAccount> findAll();
    boolean existsByUsername(String username);
}
