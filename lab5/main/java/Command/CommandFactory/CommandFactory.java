package Command.CommandFactory;

import Command.Reader.Reader;
import Manager.CollectionManager;

public interface CommandFactory {
    /**
     * some execution command
     */
    void executeCommand(String command, Reader reader, String arg, CollectionManager collectionManager);

    /**
     * @return history inputted command
     */
    String getHistory();
}
