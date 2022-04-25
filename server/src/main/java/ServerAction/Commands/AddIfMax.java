package ServerAction.Commands;

import ServerAction.FileReader;
import Content.Product;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * add new element in the collection if it is large max element of the collection
 */
public class AddIfMax{
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
