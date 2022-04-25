package ServerAction.Commands;

import ServerAction.FileReader;
import Content.Product;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * remove elements smaller than the given
 */
public class RemoveLower{
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
