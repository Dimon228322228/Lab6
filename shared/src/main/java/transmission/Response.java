package transmission;

import lombok.Getter;

public class Response {
    @Getter private final String arg;
//    @Getter private final ResponseMode responseMode;

    public Response(String arg){
//        this.responseMode = responseMode;
        this.arg = arg;
    }
}
