package engine.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.ElementCollection;
import java.util.List;

public class AnswerResponse {

    @JsonProperty("answer")
    private List<Integer> answer;

    @JsonCreator
    public AnswerResponse(@JsonProperty("answer") List<Integer> answer) {
        this.answer = answer;
    }
    @JsonProperty("answer")
    public List<Integer> getAnswers() {
        return answer;
    }

    public void setAnswers(List<Integer> answers) {
        this.answer = answers;
    }
}
