package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Content.Product;
import Manager.CollectionManager;
import Messager.Messenger;

import java.io.IOException;

/**
 * add element in the collection
 */
public class Add implements Command{
    /**
     * add product in the collection
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory) {
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
