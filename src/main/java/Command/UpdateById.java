package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Content.Product;
import Manager.CollectionManager;
import Exception.ProductNotFoundException;
import Messager.Messenger;

import java.io.IOException;

/**
 * update element of the collection by id
 */
public class UpdateById implements Command{
    /**
     * checked exist given id
     * read product from console
     * set new product by given id
     * old product has deleted
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory) throws ProductNotFoundException {
        long id;
        Product product = null;
        try{
            id = Long.parseLong(arg);
            manager.removeById(id);
            product = reader.readProduct();
        } catch (NumberFormatException e){
            System.err.println("Id must be long!");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        if (product == null) return;
        manager.autoUpdateId();
        product.setId(Long.parseLong(arg));
        manager.add(product);
        System.out.println("Added success");
    }
}
