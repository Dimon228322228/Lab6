package actionClient;

import action.Command;
import action.CommandData;
import action.ResultAction;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandController {
    @Getter @Setter private String argCommand;
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Map<String, Command> commandMap = new HashMap<>();

    @Getter @Setter List<CommandData> serverCommandsData;

    public CommandController(){
        Stream.of(new Help(this), new ExecuteScript(this), new Exit(this), new History(this))
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
        List<CommandData> commandData = commandMap.keySet().stream()
                .map(commandMap::get)
                .map(Command::getCommandData).toList();
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
        Command command = getCommand(nameCommand);
        return command.execute();
    }

    public Command getCommand(String commandName) {
        return commandMap.get(commandName);
    }
}
