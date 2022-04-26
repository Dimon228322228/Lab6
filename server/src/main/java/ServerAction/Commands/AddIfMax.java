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
 * add new element in the collection if it is large max element of the collection
 */
public class AddIfMax implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "addIfMax";
    public AddIfMax(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.PRODUCT);
    }
    /**
     * read product from the console
     * compare it with max product
     * add given product in the collection if it is large max element
     */
    public void execute(CollectionManager manager, FileReader reader) {
        Product product;
        try {
            product = reader.readProduct();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        if (product == null) return;
        manager.addIfMax(product);
    }
}
