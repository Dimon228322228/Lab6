package ServerAction.CommandFactory;

import Action.Command;
import ServerAction.FileReader;

import java.util.List;

public interface CommandFactory {
    /**
     * some execution command
     */
    void executeCommand(String command, FileReader reader, String arg);

    List<Command> getCommands();

    Command getCommand(String commandName);

    /**
     * @return history inputted command
     */
    String getHistory();
}
