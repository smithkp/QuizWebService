package engine.controller;

import engine.model.UserAccount;
import engine.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
public class RegisterController {
    @Autowired
    UserAccountService userAccountService;

    @GetMapping(value = "/api/home")
    public String getHomepage(){
        return "<h1>Welcome USER</h1>";
    }

    @GetMapping(value = "/api/accounts")
    @ResponseBody
    public List<UserAccount> getIndex(){
        return userAccountService.getAllUsers();
    }

    @PostMapping(value = "/api/register")
    public void registerUserAccount(@Valid @RequestBody UserAccount account) {
        userAccountService.saveUserAccount(account);
    }

}