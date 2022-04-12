package Command;

import Command.Reader.Reader;
import Content.Product;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * add element in the collection
 */
public class Add implements SimpleCommand{
    /**
     * add product in the collection
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
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
