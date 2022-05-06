package actionClient;

import action.Command;
import action.CommandData;
import action.TypeCommand;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandHandlerClient {
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    private final Map<String, Command> commandMap = new HashMap<>();

    @Getter @Setter List<CommandData> serverCommandsData;

    public CommandHandlerClient(){
        Stream.of(new Help(this), new ExecuteScript(this), new Exit(this), new History(this))
                .forEach(x -> commandMap.put(x.getCommandData().getName(), x));
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

    public List<CommandData> getCurrentCommandData(){
        return commandMap.keySet().stream()
                .map(commandMap::get)
                .map(Command::getCommandData)
                .collect(Collectors.toList());
    }

    /**
     * @return history command as column in console
     */
    public String getHistory(){
        return historyCommand.stream().collect(Collectors.joining(System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator()));
    }

    public String executeCommand(String nameCommand) {
        Optional<Command> command = getCommand(nameCommand);
        if (command.isPresent()){
            if (command.get().getCommandData().getTypes().contains(TypeCommand.EXECUTED)){

            }
        }
        return null;
    }

    public Optional<Command> getCommand(String commandName) {
        return Optional.of(commandMap.get(commandName));
    }
}
