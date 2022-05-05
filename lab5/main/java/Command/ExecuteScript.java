package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.FileReader;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * runs the script from file
 */
public class ExecuteScript implements Command{
    private final static Set<File> files = new HashSet<>();
    /**
     * executes a script if file correctness
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messenger, CommandFactory commandFactory){
        File file;
        try {
            file = new File(arg);
        } catch (NullPointerException ignored) {
            System.err.println("No such this file.");
            return;
        }
        if(files.contains(file)) {
            System.err.println("Error. Script recursion has been detected.");
            return;
        }
        files.add(file);
        try{
            Reader fileReader = new FileReader(commandFactory, manager, file);
            fileReader.readCommand();
        } catch (FileNotFoundException e){
            System.err.println("File with name " + file.getName() + " is not found");
        }
        files.remove(file);
    }
}
