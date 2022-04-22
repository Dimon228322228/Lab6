package Command.CommandFactory;

import Command.Command;
import Command.Reader.Reader;
import Manager.CollectionManager;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

import Exception.UnknownCommandException;
import Messager.Messenger;

/**
 * A class determines what type of command was entered and stores the command history
 */
public class CommandFactoryImpl implements CommandFactory {
    private static CommandFactoryImpl instance = null;
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Map<String, Command> commands;
    private final Messenger messenger;

    private CommandFactoryImpl(Map<String, Command> commands, Messenger messenger) {
        this.commands = commands;
        this.messenger = messenger;
    }

    /**
     * @return single object this class (there must be one instance)
     */
    public static CommandFactory getInstance(Map<String, Command> commands, Messenger messenger){
        if (instance == null) instance = new CommandFactoryImpl(commands, messenger);
        return instance;
    }

    /**
     * @return command from string
     */
    private Command getCommand(String commandName) {
        return commands.get(commandName);
    }

    /**
     * receives command form string, checked that it is correct
     * add given command in history if it is correct
     * calls a method to execute a certain type of command
     */
    @Override
    public void executeCommand(String commandName, Reader reader, String arg, CollectionManager collectionManager) {
        Command command = getCommand(commandName);
        if (commandName.equals("") || command == null){
            System.err.println((new UnknownCommandException()).getMessage());
            return;
        }
        addCommandInHistory(commandName);
        try {
            command.execute(collectionManager, reader, arg, messenger, this);
        } catch (Exception e){
            System.err.println(e.getMessage());
        }
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
