package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Exceptions.ProductNotFoundException;
import Manager.CollectionManager;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * removing element by id
 */
public class RemoveById implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "removeById";
    public RemoveById(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.PRODUCT);
        type.add(TypeCommand.ARG);
    }
    /**
     * read id from console
     * remove element of the collection by id
     */
    public void execute(CollectionManager manager, String arg) throws ProductNotFoundException{
        long id;
        try{
            id = Long.parseLong(arg);
            manager.removeById(id);
        } catch (NumberFormatException e){
            System.err.println("Id must be long!");
        }
    }
}