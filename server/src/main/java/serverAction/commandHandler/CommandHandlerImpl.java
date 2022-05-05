package serverAction.commandHandler;

import action.Command;
import action.CommandData;
import action.TypeCommand;
import serverAction.commands.*;
import serverAction.FileReader;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

/**
 * A class determines what type of command was entered and stores the command history
 */
public class CommandHandlerImpl implements CommandHandler {
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Set<Command> commands = Set.of(
            new Add(), new AddIfMax(), new Clear(), new CountByManufactureCost(), new CountGreaterThenUnitOfMeasure(),
            new DisplayInfo(), new ExecuteScript(), new Exit(), new Help(), new History(), new PrintInAscendingOrder(),
            new RemoveById(), new RemoveLower(), new Save(), new ShowElements(), new UpdateById());


    public List<CommandData> getCommandDataForUser(){
        return commands.stream()
                .map(Command::getCommandData)
                .filter(commandData -> commandData.getTypes().contains(TypeCommand.USER))
                .collect(Collectors.toList());
    }

    /**
     * @return command from string
     */
    public Command getCommand(String commandName) {
        return commands.stream()
                .filter(x -> x.getCommandData().getName().equals(commandName)).toList().get(0);
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
