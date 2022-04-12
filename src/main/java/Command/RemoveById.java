package Command;

import Command.Reader.Reader;
import Manager.CollectionManager;
import Exception.ProductNotFoundException;

/**
 * removing element by id
 */
public class RemoveById implements SimpleCommand{
    boolean flag;
    /**
     * read id from console
     * remove element of the collection by id
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg) {
        flag = true;
        long id;
        try{
            id = Long.parseLong(arg);
        } catch (NumberFormatException e){
            System.err.println("Id must be long.");
            flag = false;
            return;
        }
        try {
            manager.removeById(id);
        } catch (ProductNotFoundException e){
            System.err.println(e.getMessage());
            flag = false;
            return;
        }
        System.out.println("Removing success");
    }
}
