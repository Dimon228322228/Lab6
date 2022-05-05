package actionClient;

import action.Command;
import action.CommandData;
import lombok.Getter;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;

public class CommandHandlerClient {
    private final ArrayBlockingQueue<String> historyCommand = new ArrayBlockingQueue<>(13);
    Set<Command> commands = Set.of(new Help(), new ExecuteScript(), new Exit(), new History());
    @Getter  List<CommandData> commandData;

    public CommandHandlerClient(List<CommandData> commandData){
        this.commandData = commandData;
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

    public void executeCommand() {

    }

    public Command getCommand(String commandName) {
        return null;
    }
}
