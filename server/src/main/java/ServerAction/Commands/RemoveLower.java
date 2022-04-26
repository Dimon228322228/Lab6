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
 * remove elements smaller than the given
 */
public class RemoveLower implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "removeLower";
    public RemoveLower(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.PRODUCT);
    }
    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    public void execute(CollectionManager manager, FileReader reader) {
        Product product;
        try{
            product = reader.readProduct();
        } catch (IOException e){
            System.err.println(e.getMessage());
            return;
        }
        if (product == null) return;
        manager.removeLower(product);
    }
}
