package transmission;

import action.ResultAction;
import lombok.Getter;

import java.io.Serializable;

public class Response implements Serializable {
    @Getter private final ResultAction resultAction;

    public Response(ResultAction resultAction){
        this.resultAction = resultAction;
    }
}
