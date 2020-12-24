package engine.model;

import java.time.LocalDateTime;

public interface CompletedQuizResponse {
    Long getId();
    LocalDateTime getCompletedAt();
}
