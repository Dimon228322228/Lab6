package Transmission;

import Action.Command;
import Content.Product;
import lombok.Getter;

public class Request implements Message{
    @Getter private final Product product;
    @Getter private final String arg;
    @Getter private final Command command;

    public Request(Product product, String arg, Command command) {
        this.product = product;
        this.arg = arg;
        this.command = command;
    }
}
