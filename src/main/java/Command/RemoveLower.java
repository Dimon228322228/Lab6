package Command;

import Command.Reader.Reader;
import Content.Product;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * remove elements smaller than the given
 */
public class RemoveLower implements SimpleCommand{

    /**
     * read product from console
     * removes all smaller elements by Comparable
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
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
