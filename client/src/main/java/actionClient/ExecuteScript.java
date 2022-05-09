package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import reader.ExchangeController;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * runs the script from file
 */
public class ExecuteScript extends AbstractCommandClient {
    private final static Set<File> files = new HashSet<>();
    private final ExchangeController exchangeController;
    private final CommandHandler commandHandler;
    public ExecuteScript(CommandController comContr, ExchangeController exchangeController, CommandHandler commandHandler){
        super("executeScript",
                Set.of(TypeCommand.USER, TypeCommand.EXECUTED, TypeCommand.ARG),
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
        } catch (NullPointerException ignored) {
            return new ResultAction(State.ERROR, "No such this file.");
        }
        if(!files.add(file)) {
            return new ResultAction(State.ERROR, "Error. Script recursion has been detected.");
        }
        try {
            exchangeController.replaceIn(new BufferedReader(new FileReader(file)))
                    .replaceOut(new BufferedWriter(new StringWriter()));
        } catch (FileNotFoundException e) {
            return new ResultAction(State.ERROR, "No such this file.");
        }
        commandHandler.run();
        files.remove(file);
        exchangeController.replaceOut(new BufferedWriter(System.console().writer()))
                .replaceIn(new BufferedReader(System.console().reader()));
        return new ResultAction(State.SUCCESS, "File " + file.getAbsolutePath() + "has been executed");
    }
}
