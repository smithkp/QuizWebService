package engine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "Accounts")
public class UserAccount {
    @Id
    @GeneratedValue
    @Column(name = "id")
    Long id;

    @Email(message = "incorrect email format")
    @Column(name = "email",nullable = false, unique = true)
    @JsonProperty("email")
    @NotNull
    @Pattern(regexp = ".+@.+\\..+")
    private String username;

    @NotEmpty
    @Size(min = 5, message = "password must contain at least 5 characters")
    private String password;

    @OneToMany(mappedBy = "account")
    private List<Quiz> createdQuizzes = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<CompletedQuiz> completions = new ArrayList<>();

    public UserAccount(){
    }

    public UserAccount(@Email String username, @NotNull @Size(min = 5, message = "password must contain at least 5 characters") String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
