package engine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String text;

    @NotEmpty
    @Size(min = 2 , message = "Incorrect number of answer options (must have 2-4)")
    private String[] options;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ElementCollection(targetClass = Integer.class)
    @Column(name = "answer")
    private List<Integer> answer;

    @ManyToOne
    @JoinColumn(name = "user")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private UserAccount account;

    public Quiz(String title, String text, String[] options, List<Integer> answer, UserAccount account) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer == null? new ArrayList<Integer>() : answer;
    }

    public Quiz(){}

    public long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    @JsonIgnore
    public UserAccount getAccount() {
        return account;
    }

    public void setAccount(UserAccount account) {
        this.account = account;
    }



}
