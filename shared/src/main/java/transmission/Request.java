package transmission;

import content.Product;
import lombok.Getter;

import java.io.Serializable;

public class Request implements Serializable {
    @Getter private final Product product;
    @Getter private final String arg;
    @Getter private final String commandName;

    public Request(Product product, String arg, String commandName) {
        this.product = product;
        this.arg = arg;
        this.commandName = commandName;
    }
}
