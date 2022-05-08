package action;

import lombok.Getter;

public class ResultAction {
    @Getter private final State state;
    @Getter private final String description;
    public ResultAction(State state, String description){
        this.state = state;
        this.description = description;
    }
}
