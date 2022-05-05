package serverAction.commandHandler;

import action.Command;
import action.CommandData;
import serverAction.FileReader;

import java.util.List;

public interface CommandHandler {
    /**
     * some execution command
     */
    void executeCommand(String command, FileReader reader, String arg);

    List<CommandData> getCommandDataForUser();

    Command getCommand(String commandName);

    /**
     * @return history inputted command
     */
    String getHistory();
}
