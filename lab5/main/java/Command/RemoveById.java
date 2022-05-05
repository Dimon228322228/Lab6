package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Exception.ProductNotFoundException;
import Messager.Messenger;

/**
 * removing element by id
 */
public class RemoveById implements Command{
    /**
     * read id from console
     * remove element of the collection by id
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory) throws ProductNotFoundException{
        long id;
        try{
            id = Long.parseLong(arg);
            manager.removeById(id);
        } catch (NumberFormatException e){
            System.err.println("Id must be long!");
        }
    }
}
