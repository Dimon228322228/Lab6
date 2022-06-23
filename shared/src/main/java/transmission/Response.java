package transmission;

import action.CommandData;
import action.ResultAction;
import content.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    @Getter private final ResultAction resultAction;
    @Setter @Getter private List<CommandData> commandData;
    @Setter @Getter private List<Product> collection;

    public Response(ResultAction resultAction){
        this.resultAction = resultAction;
    }
}
