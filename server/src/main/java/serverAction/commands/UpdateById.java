package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;
import exceptions.ProductNotFoundException;

import java.util.Set;

/**
 * update element of the collection by id
 */
public class UpdateById extends AbstractCommand {
    public UpdateById(){
        super("updateById",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT, TypeCommand.ARG),
                "update the value of the collection element whose id is equal to the given one");
    }

    /**
     * checked exist given id
     * read product from console
     * set new product by given id
     * old product has deleted
     */
    public void execute() throws ProductNotFoundException {
//        long id;
//        Product product = null;
//        try{
//            id = Long.parseLong(arg);
//            manager.removeById(id);
//            product = reader.readProduct();
//        } catch (NumberFormatException e){
//            System.err.println("Id must be long!");
//        } catch (IOException e) {
//            System.err.println(e.getMessage());
//            return;
//        }
//        if (product == null) return;
//        manager.autoUpdateId();
//        product.setId(Long.parseLong(arg));
//        manager.add(product);
//        System.out.println("Added success");
    }
}
