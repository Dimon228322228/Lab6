package Transmission;

import Action.Command;
import Content.Product;
import lombok.Getter;

public class Response implements Message{
    @Getter private final Product product;
    @Getter private final String arg = "";
    @Getter private final Command command;

    public Response(Product product, Command command){
        this.product = product;
        this.command = command;
    }
}
