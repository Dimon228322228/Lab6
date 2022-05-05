package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;
import content.Product;

import java.util.Set;

/**
 * add element in the collection
 */
public class Add extends AbstractCommand {
    public Add(){
        super("add",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT),
                "add a new {Product} to the collection");
    }
    /**
     * add product in the collection
     */
    public void execute() {
        Product product;
//        try{
//            product = reader.readProduct();
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            return;
//        }
//        if (product == null) return;
//        manager.add(product);
        System.out.println("Product has been added successful.");
    }
}
