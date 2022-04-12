package Command;

import Command.Reader.Reader;
import Manager.CollectionManager;

/**
 * a general interface for describing simple commands without outputting anything to the console
 */
public interface SimpleCommand extends Command{
    void execute(CollectionManager manager, Reader reader, String arg);
}
