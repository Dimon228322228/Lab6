package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Content.Product;
import Manager.CollectionManager;
import Messager.Messenger;

import java.io.IOException;

/**
 * add new element in the collection if it is large max element of the collection
 */
public class AddIfMax implements Command{
    /**
     * read product from the console
     * compare it with max product
     * add given product in the collection if it is large max element
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory) {
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
