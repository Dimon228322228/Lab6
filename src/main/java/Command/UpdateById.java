package Command;

import Command.Reader.Reader;
import Content.Product;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * update element of the collection by id
 */
public class UpdateById implements SimpleCommand{
    /**
     * checked exist given id
     * read product from console
     * set new product by given id
     * old product has deleted
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
        RemoveById remove = new RemoveById();
        remove.execute(manager, reader, arg);
        if (!remove.flag) return;
        Product Product;
        try{
            Product = reader.readProduct();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        if (Product == null) return;
        manager.autoUpdateId();
        Product.setId(Long.parseLong(arg));
        manager.add(Product);
        System.out.println("Added success");
    }
}
