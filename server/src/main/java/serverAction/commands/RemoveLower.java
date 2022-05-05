package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * remove elements smaller than the given
 */
public class RemoveLower extends AbstractCommand {
    public RemoveLower(){
        super("removeLower",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT),
                "remove all elements from the collection that are smaller than the given one");
    }
    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    public void execute() {
//        Product product;
//        try{
//            product = reader.readProduct();
//        } catch (IOException e){
//            System.err.println(e.getMessage());
//            return;
//        }
//        if (product == null) return;
//        manager.removeLower(product);
    }
}
