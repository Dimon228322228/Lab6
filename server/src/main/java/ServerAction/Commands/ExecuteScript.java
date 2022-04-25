package ServerAction.Commands;

import ServerAction.CommandFactory.CommandFactory;
import ServerAction.FileReader;
import Manager.CollectionManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * runs the script from file
 */
public class ExecuteScript {
    private final static Set<File> files = new HashSet<>();
    /**
     * executes a script if file correctness
     */
    public void execute(String arg, CommandFactory commandFactory){
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
            FileReader fileReader = new FileReader(commandFactory, file);
            fileReader.readCommand();
        } catch (FileNotFoundException e){
            System.err.println("File with name " + file.getName() + " is not found");
        }
        files.remove(file);
    }
}