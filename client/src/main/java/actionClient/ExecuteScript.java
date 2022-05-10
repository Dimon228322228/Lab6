package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import reader.ExchangeController;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * runs the script from file
 */
public class ExecuteScript extends AbstractCommandClient {
    private final Set<File> files = new HashSet<>();
    private final Stack<BufferedReader> readers = new Stack<>();
    private final ExchangeController exchangeController;
    private final CommandHandler commandHandler;
    public ExecuteScript(CommandController comContr, ExchangeController exchangeController, CommandHandler commandHandler){
        super("executeScript",
                Set.of(TypeCommand.USER, TypeCommand.ARG),
                "read and execute the script from the specified file");
        this.comContr = comContr;
        this.exchangeController = exchangeController;
        this.commandHandler = commandHandler;
    }
    /**
     * executes a script if file correctness
     */
    public ResultAction execute(){
        File file;
        try {
            file = new File(comContr.getArgCommand());
        } catch (NullPointerException e) {
            return new ResultAction(State.ERROR, "No such this file. \n");
        }
        if(!files.add(file)) return new ResultAction(State.ERROR, "Error. Script recursion has been detected. \n");
        if (files.size() > 10){
            exchangeController.replaceIn(readers.pop());
            return new ResultAction(State.ERROR, "Error. There is too many sub-calls. \n");
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            readers.push(reader);
            exchangeController.replaceIn(reader);
        } catch (FileNotFoundException e) {
            return new ResultAction(State.ERROR, "No such this file. \n");
        }
        commandHandler.setExecutionMode(true);
        commandHandler.run();
        files.remove(file);
        readers.pop();
        if (files.size() != 0){
            exchangeController.replaceIn(readers.peek());
        } else{
            exchangeController.replaceIn(new BufferedReader(new InputStreamReader(System.in)));
            commandHandler.setExecutionMode(false);
        }
        commandHandler.setFlag();
        return new ResultAction(State.SUCCESS, "File " + file.getAbsolutePath() + " has been executed \n");
    }
}
