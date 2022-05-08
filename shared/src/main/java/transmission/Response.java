package transmission;

import action.ResultAction;
import lombok.Getter;

public class Response {
    @Getter private final ResultAction resultAction;

    public Response(ResultAction resultAction){
        this.resultAction = resultAction;
    }
}
