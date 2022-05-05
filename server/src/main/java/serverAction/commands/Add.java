package serverAction.commands;

import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * add element in the collection
 */
public class Add extends AbstractCommandServer {
    public Add(ExecutionResources executionResources){
        super("add",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT),
                "add a new {Product} to the collection");
        this.executionResources = executionResources;
    }
    /**
     * add product in the collection
     */
    public String execute() throws InvalidProductFieldException{
        Product product = executionResources.getProduct();
        if (product == null) throw new InvalidProductFieldException("Haven't got any product. Nothing adding. ");
        executionResources.getCollectionManager().add(product);
        return "Product has been added successful. ";
    }
}
