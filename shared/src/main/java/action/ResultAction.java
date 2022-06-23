package action;

import content.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class ResultAction implements Serializable {
    @Getter private final State state;
    @Getter private final String description;
    @Setter @Getter private List<Product> collection;
    public ResultAction(State state, String description){
        this.state = state;
        this.description = description;
    }
}
