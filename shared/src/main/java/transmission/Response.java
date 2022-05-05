package transmission;

import content.Product;
import lombok.Getter;

public class Response {
    @Getter private final Product product;
    @Getter private final String arg = "";
    @Getter private final String commandName;

    public Response(Product product, String commandName){
        this.product = product;
        this.commandName = commandName;
    }
}
