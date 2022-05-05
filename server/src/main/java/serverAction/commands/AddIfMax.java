package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * add new element in the collection if it is large max element of the collection
 */
public class AddIfMax extends AbstractCommand {
    public AddIfMax(){
        super("addIfMax",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT),
                "add a new element to the collection if its value is greater than the value of the largest element in this collection");
    }
    /**
     * read product from the console
     * compare it with max product
     * add given product in the collection if it is large max element
     */
    public void execute() {
//        Product product;
//        try {
//            product = reader.readProduct();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            return;
//        }
//        if (product == null) return;
//        manager.addIfMax(product);
    }
}
