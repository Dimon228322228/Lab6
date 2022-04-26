package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import ServerAction.FileReader;
import Content.Product;
import Manager.CollectionManager;
import lombok.Getter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * add element in the collection
 */
public class Add implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "add";
    public Add(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.PRODUCT);
    }
    /**
     * add product in the collection
     */
    public void execute(CollectionManager manager, FileReader reader) {
        Product product;
        try{
            product = reader.readProduct();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        if (product == null) return;
        manager.add(product);
        System.out.println("Product has been added successful.");
    }
}
