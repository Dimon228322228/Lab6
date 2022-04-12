package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.FileReader;
import Command.Reader.Reader;
import Manager.CollectionManager;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * runs the script from file
 */
public class ExecuteScript implements ScriptCommand{
    /**
     * executes a script if file correctness
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, File file, CommandFactory commandFactory){
        try{
            Reader fileReader = new FileReader(commandFactory, manager, file);
            fileReader.readCommand();
        } catch (FileNotFoundException e){
            System.err.println("File with name " + file.getName() + " is not found");
        }
    }
}
