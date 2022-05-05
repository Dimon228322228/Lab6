package serverAction;

import action.Command;
import action.CommandData;
import action.TypeCommand;
import manager.CollectionManager;
import manager.QueueManager;
import serverAction.commands.*;

import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class determines what type of command was entered and stores the command history
 */
public class CommandHandler {
    CollectionManager colManag = QueueManager.getInstance();
    ExecutionResources execRes = new ExecutionResources(colManag);
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandHandler() throws JAXBException {
        Stream.of(new Add(execRes), new AddIfMax(execRes), new Clear(execRes), new CountByManufactureCost(execRes),
                new CountGreaterThenUnitOfMeasure(execRes), new DisplayInfo(execRes), new PrintInAscendingOrder(execRes),
                        new RemoveById(execRes), new RemoveLower(execRes), new Save(execRes), new ShowElements(execRes),
                        new UpdateById(execRes)).forEach(x -> commandMap.put(x.getCommandData().getName(), x));
    }


    public List<CommandData> getCommandDataForUser(){
        return commandMap.keySet().stream()
                .filter(x -> commandMap.get(x).getCommandData().getTypes().contains(TypeCommand.USER))
                .map(x -> commandMap.get(x).getCommandData())
                .collect(Collectors.toList());
    }

    /**
     * @return command from string
     */
    public Command getCommand(String commandName) {
        return commandMap.get(commandName);
    }

    /**
     * receives command form string, checked that it is correct
     * add given command in history if it is correct
     * calls a method to execute a certain type of command
     */
    public void executeCommand() {
//        Object command = getCommand(commandName);
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

}
