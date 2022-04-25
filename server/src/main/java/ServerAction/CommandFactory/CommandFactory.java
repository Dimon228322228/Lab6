package ServerAction.CommandFactory;

import ServerAction.FileReader;

public interface CommandFactory {
    /**
     * some execution command
     */
    void executeCommand(String command, FileReader reader, String arg);

    /**
     * @return history inputted command
     */
    String getHistory();
}
