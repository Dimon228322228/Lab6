package serverAction.commands;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import content.Product;
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
                Set.of(TypeCommand.EXTERNAL, TypeCommand.PRODUCT, TypeCommand.ARG),
                "update the value of the collection element whose id is equal to the given one");
        this.executionResources = executionResources;
    }

    /**
     * checked exist given id
     * read product from console
     * set new product by given id
     * old product has deleted
     */
    public ResultAction execute() throws ProductNotFoundException {
        long id;
        Product product = executionResources.getProduct();
        try{
            id = Long.parseLong(executionResources.getArg());
            executionResources.getCollectionManager().removeById(id);
        } catch (NumberFormatException e){
            return new ResultAction(State.ERROR, "Id must be long! \n");
        } catch (ProductNotFoundException e){
            return new ResultAction(State.FAILED, e.getMessage());
        }
        if (product == null) return new ResultAction(State.ERROR, "Haven't got any product. Nothing adding. \n");
        product.setId(id);
        executionResources.getCollectionManager().add(product);
        return new ResultAction(State.SUCCESS, "Added success \n");
    }
}
