package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * runs the script from file
 */
public class ExecuteScript extends AbstractCommand {
    private final static Set<File> files = new HashSet<>();
    public ExecuteScript(){
        super("executeScript",
                Set.of(TypeCommand.USER, TypeCommand.EXECUTED, TypeCommand.ARG),
                "read and execute the script from the specified file");
    }
    /**
     * executes a script if file correctness
     */
    public void execute(){
//        File file;
//        try {
//            file = new File(arg);
//        } catch (NullPointerException ignored) {
//            System.err.println("No such this file.");
//            return;
//        }
//        if(files.contains(file)) {
//            System.err.println("Error. Script recursion has been detected.");
//            return;
//        }
//        files.add(file);
//        try{
//            FileReader fileReader = new FileReader(commandFactory, file);
//            fileReader.readCommand();
//        } catch (FileNotFoundException e){
//            System.err.println("File with name " + file.getName() + " is not found");
//        }
//        files.remove(file);
    }
}
