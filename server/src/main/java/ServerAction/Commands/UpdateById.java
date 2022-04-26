package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import ServerAction.FileReader;
import Content.Product;
import Exceptions.ProductNotFoundException;
import Manager.CollectionManager;
import lombok.Getter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * update element of the collection by id
 */
public class UpdateById implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "updateById";

    public UpdateById(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.PRODUCT);
        type.add(TypeCommand.ARG);
    }

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
