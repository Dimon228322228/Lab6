package serverAction;

import action.CommandData;
import action.ResultAction;
import action.TypeCommand;
import lombok.Setter;
import manager.CollectionManager;
import manager.QueueManager;
import manager.database.DatabaseManager;
import serverAction.commands.*;
import transmission.Request;

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
    @Setter
    Request request;
    private final Map<String, AbstractCommandServer> commandMap = new HashMap<>();

    public CommandHandler() {
        Stream.of(new Add(execRes), new AddIfMax(execRes), new Clear(execRes), new CountByManufactureCost(execRes),
                new CountGreaterThenUnitOfMeasure(execRes), new DisplayInfo(execRes), new PrintInAscendingOrder(execRes),
                        new RemoveById(execRes), new RemoveLower(execRes), new Save(execRes), new ShowElements(execRes),
                        new UpdateById(execRes)).forEach(x -> commandMap.put(x.getCommandData().getName(), x));
    }


    public List<CommandData> getCommandDataForUser(){
        return commandMap.keySet().stream()
                .filter(x -> commandMap.get(x).getCommandData().getTypes().contains(TypeCommand.EXTERNAL))
                .map(x -> commandMap.get(x).getCommandData())
                .collect(Collectors.toList());
    }

    /**
     * @return command from string
     */
    public AbstractCommandServer getCommand(String commandName) {
        return commandMap.get(commandName);
    }

    /**
     * receives command form string, checked that it is correct
     * add given command in history if it is correct
     * calls a method to execute a certain type of command
     */
    public ResultAction executeCommand(String commandName) {
        AbstractCommandServer command = getCommand(commandName);
        if (command.getCommandData().getTypes().contains(TypeCommand.ARG)) setArg(command);
        if (command.getCommandData().getTypes().contains(TypeCommand.PRODUCT)) setProduct(command);
        return command.execute();
    }

    private void setArg(AbstractCommandServer command){
        command.getExecutionResources().setArg(request.getArg());
    }

    private void setProduct(AbstractCommandServer command){
        command.getExecutionResources().setProduct(request.getProduct());
    }

    public void setDatabaseManagerToExecutionResources(DatabaseManager databaseManager){
        execRes.setDatabaseManager(databaseManager);
    }
}
