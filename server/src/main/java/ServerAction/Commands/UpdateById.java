package ServerAction.Commands;

import ServerAction.FileReader;
import Content.Product;
import Exception.ProductNotFoundException;
import Manager.CollectionManager;

import java.io.IOException;

/**
 * update element of the collection by id
 */
public class UpdateById{
    /**
     * checked exist given id
     * read product from console
     * set new product by given id
     * old product has deleted
     */
    public void execute(CollectionManager manager, FileReader reader, String arg) throws ProductNotFoundException {
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
