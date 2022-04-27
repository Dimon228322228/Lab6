package Transmission;

import Action.Command;
import Content.Product;

public interface Message {
    Command getCommand();
    Product getProduct();
    String getArg();
}
