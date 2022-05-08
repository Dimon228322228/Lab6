package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * runs the script from file
 */
public class ExecuteScript extends AbstractCommandClient {
    private final static Set<File> files = new HashSet<>();
    public ExecuteScript(CommandController comContr){
        super("executeScript",
                Set.of(TypeCommand.USER, TypeCommand.EXECUTED, TypeCommand.ARG),
                "read and execute the script from the specified file");
        this.comContr = comContr;
    }
    /**
     * executes a script if file correctness
     */
    public ResultAction execute(){
        File file;
        try {
            file = new File(comContr.getArgCommand());
        } catch (NullPointerException ignored) {
            return new ResultAction(State.ERROR, "No such this file.");
        }
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
        return null;
    }
}
