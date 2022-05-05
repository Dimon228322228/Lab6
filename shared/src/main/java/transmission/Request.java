package transmission;

import content.Product;
import lombok.Getter;

public class Request {
    @Getter private final Product product;
    @Getter private final String arg;
    @Getter private final String commandName;

    public Request(Product product, String arg, String commandName) {
        this.product = product;
        this.arg = arg;
        this.commandName = commandName;
    }
}
