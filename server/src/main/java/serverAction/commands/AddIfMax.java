package serverAction.commands;

import action.TypeCommand;
import content.Product;
import exceptions.InvalidProductFieldException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import java.util.Set;

/**
 * add new element in the collection if it is large max element of the collection
 */
public class AddIfMax extends AbstractCommandServer {
    public AddIfMax(ExecutionResources executionResources){
        super("addIfMax",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT),
                "add a new element to the collection if its value is greater than the value of the largest element in this collection");
        this.executionResources = executionResources;
    }
    /**
     * read product from the console
     * compare it with max product
     * add given product in the collection if it is large max element
     */
    public String execute() {
        Product product = executionResources.getProduct();
        if (product == null) throw new InvalidProductFieldException("Haven't got any product. Nothing compare and add. ");
        if (executionResources.getCollectionManager().addIfMax(product)) return "The product has been added. ";
        else return "The product hasn't been added because it is not largest. ";
    }
}
