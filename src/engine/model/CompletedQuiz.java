package engine.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "Completions")
@Entity
public class CompletedQuiz {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "completionId")
    private Long completionId;

    @Column(name = "id")
    private Long id;

    @Column(name = "completedAt", nullable = false, unique = false)
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    //@JsonIgnore
    UserAccount account;

    public CompletedQuiz(Long id, LocalDateTime completedAt, UserAccount account) {
        this.id = id;
        this.completedAt = completedAt;
        this.account = account;
    }

    public CompletedQuiz() {

    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public Long getCompletionId() {
        return completionId;
    }

    public void setCompletionId(long completionId) {
        this.completionId = completionId;
    }

    public UserAccount getUser() {
        return account;
    }
}
