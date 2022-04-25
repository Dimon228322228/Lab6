package ServerAction.Commands;

import ServerAction.FileReader;
import Content.Product;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * add element in the collection
 */
public class Add{
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
