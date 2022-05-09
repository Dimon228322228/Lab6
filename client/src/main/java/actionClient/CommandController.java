package actionClient;

import action.Command;
import action.CommandData;
import action.ResultAction;
import lombok.Getter;
import lombok.Setter;
import reader.ExchangeController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandController {
    @Getter @Setter private String argCommand;
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Map<String, AbstractCommandClient> commandMap = new HashMap<>();

    @Getter @Setter List<CommandData> serverCommandsData;

    public CommandController(CommandHandler commandHandler, ExchangeController exchangeController){
        Stream.of(new Help(this), new ExecuteScript(this, exchangeController, commandHandler),
                        new Exit(this), new History(this))
                .forEach(x -> commandMap.put(x.getCommandData().getName(), x));
    }

    /**
     * add command in history
     * if history is larger than 13, the first one added will be deleted
     */
    public void addCommandInHistory(String command){
        if (!historyCommand.offer(command)){
            historyCommand.remove();
            historyCommand.add(command);
        }
    }

    public List<CommandData> getCurrentCommandData(){
        return commandMap.keySet().stream()
                .map(commandMap::get)
                .map(Command::getCommandData)
                .collect(Collectors.toList());
    }

    public List<CommandData> getFullCommandData(){
        List<CommandData> commandData = new ArrayList<>( commandMap.keySet().stream()
                .map(commandMap::get)
                .map(Command::getCommandData).toList());
        commandData.addAll(serverCommandsData);
        return commandData;
    }

    /**
     * @return history command as column in console
     */
    public String getHistory(){
        return historyCommand.stream().collect(Collectors.joining(System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator()));
    }

    public ResultAction executeCommand(String nameCommand) {
        AbstractCommandClient command = getCommand(nameCommand);
        return command.execute();
    }

    public AbstractCommandClient getCommand(String commandName) {
        return commandMap.get(commandName);
    }
}
