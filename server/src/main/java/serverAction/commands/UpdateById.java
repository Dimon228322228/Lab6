package serverAction.commands;

import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
import exceptions.ProductNotFoundException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * update element of the collection by id
 */
public class UpdateById extends AbstractCommandServer {
    public UpdateById(ExecutionResources executionResources){
        super("updateById",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT, TypeCommand.ARG),
                "update the value of the collection element whose id is equal to the given one");
        this.executionResources = executionResources;
    }

    /**
     * checked exist given id
     * read product from console
     * set new product by given id
     * old product has deleted
     */
    public String execute() throws ProductNotFoundException {
        long id;
        Product product = executionResources.getProduct();
        try{
            id = Long.parseLong(executionResources.getArg());
            executionResources.getCollectionManager().removeById(id);
        } catch (NumberFormatException e){
            throw new InvalidProductFieldException("Id must be long!");
        }
        if (product == null) throw new InvalidProductFieldException("Haven't got any product. Nothing adding. ");
        executionResources.getCollectionManager().autoUpdateId();
        product.setId(id);
        executionResources.getCollectionManager().add(product);
        return "Added success";
    }
}
