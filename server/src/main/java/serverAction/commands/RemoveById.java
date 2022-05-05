package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;
import exceptions.ProductNotFoundException;

import java.util.Set;

/**
 * removing element by id
 */
public class RemoveById extends AbstractCommand {
    public RemoveById(){
        super("removeById",
                Set.of(TypeCommand.USER, TypeCommand.PRODUCT, TypeCommand.ARG),
                "remove element from collection by its id");
    }
    /**
     * read id from console
     * remove element of the collection by id
     */
    public void execute() throws ProductNotFoundException{
//        long id;
//        try{
//            id = Long.parseLong(arg);
//            manager.removeById(id);
//        } catch (NumberFormatException e){
//            System.err.println("Id must be long!");
//        }
    }
}