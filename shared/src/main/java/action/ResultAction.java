package action;

import lombok.Getter;

import java.io.Serializable;

public class ResultAction implements Serializable {
    @Getter private final State state;
    @Getter private final String description;
    public ResultAction(State state, String description){
        this.state = state;
        this.description = description;
    }
}
