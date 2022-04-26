package ServerAction.CommandFactory;

import Action.Command;
import Action.TypeCommand;
import ServerAction.Commands.*;
import ServerAction.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * A class determines what type of command was entered and stores the command history
 */
public class CommandFactoryImpl implements CommandFactory {
    private static CommandFactoryImpl instance = null;
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Map<String, Command> commands = new HashMap<>();

    private CommandFactoryImpl() {
        setCommands();
    }

    private void setCommands(){
        commands.put("add", new Add());
        commands.put("addIfMax", new AddIfMax());
        commands.put("clear", new Clear());
        commands.put("countByManufactureCost", new CountByManufactureCost());
        commands.put("countGreaterThenUnitOfMeasure", new CountGreaterThenUnitOfMeasure());
        commands.put("displayInfo", new DisplayInfo());
        commands.put("executeScript", new ExecuteScript());
        commands.put("exit", new Exit());
        commands.put("help", new Help());
        commands.put("history", new History());
        commands.put("printInAscendingOrder", new PrintInAscendingOrder());
        commands.put("removeById", new RemoveById());
        commands.put("removeLower", new RemoveLower());
        commands.put("save", new Save());
        commands.put("showElements", new ShowElements());
        commands.put("updateById", new UpdateById());
    }

    public List<Command> getCommands(){
        return commands.keySet()
                .stream()
                .sorted()
                .map(commands::get)
                .filter(x -> x.getType().contains(TypeCommand.USER))
                .collect(Collectors.toList());
    }

    /**
     * @return single object this class (there must be one instance)
     */
    public static CommandFactory getInstance(){
        if (instance == null) instance = new CommandFactoryImpl();
        return instance;
    }

    /**
     * @return command from string
     */
    public Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    /**
     * receives command form string, checked that it is correct
     * add given command in history if it is correct
     * calls a method to execute a certain type of command
     */
    @Override
    public void executeCommand(String commandName, FileReader reader, String arg) {
        Object command = getCommand(commandName);
//        if (commandName.equals("") || command == null){
//            System.err.println((new UnknownCommandException()).getMessage());
//            return;
//        }
//        addCommandInHistory(commandName);
//        try {
//            command.execute(collectionManager, reader, arg, messenger, this);
//        } catch (Exception e){
//            System.err.println(e.getMessage());
//        }
    }

    /**
     * add command in history
     * if history is larger than 13, the first one added will be deleted
     */
    private void addCommandInHistory(String command){
        if (!historyCommand.offer(command)){
            historyCommand.remove();
            historyCommand.add(command);
        }
    }

    /**
     * @return history command as column in console
     */
    public String getHistory(){
        return historyCommand.stream().collect(Collectors.joining(System.lineSeparator(),
                                                                  System.lineSeparator(),
                                                                  System.lineSeparator()));
    }
}
