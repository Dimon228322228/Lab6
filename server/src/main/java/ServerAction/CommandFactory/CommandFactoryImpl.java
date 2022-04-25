package ServerAction.CommandFactory;

import Manager.CollectionManager;
import Manager.QueueManager;
import ServerAction.Commands.*;
import ServerAction.FileReader;
import Exception.UnknownCommandException;
import Messager.Messenger;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * A class determines what type of command was entered and stores the command history
 */
public class CommandFactoryImpl implements CommandFactory {
    private static CommandFactoryImpl instance = null;
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Map<String, Object> commands = new HashMap<>();
    private final Messenger messenger;
    private final CollectionManager collectionManager;

    private CommandFactoryImpl(Messenger messenger) throws JAXBException, FileNotFoundException {
        this.messenger = messenger;
        setCommands();
        collectionManager = QueueManager.getInstance();
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

    /**
     * @return single object this class (there must be one instance)
     */
    public static CommandFactory getInstance(Messenger messenger) throws JAXBException, FileNotFoundException {
        if (instance == null) instance = new CommandFactoryImpl(messenger);
        return instance;
    }

    /**
     * @return command from string
     */
    private Object getCommand(String commandName) {
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
