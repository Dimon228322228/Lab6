package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;

import java.io.File;

/**
 * a general interface for describing executing command
 */
public interface ScriptCommand extends Command{
    void execute(CollectionManager manager, Reader reader, File arg, CommandFactory commandFactory);
}
