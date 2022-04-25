package ServerAction.Commands;

import Exception.ProductNotFoundException;
import Manager.CollectionManager;

/**
 * removing element by id
 */
public class RemoveById{
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
