package ServerAction.Commands;

import ServerAction.FileReader;

/**
 * terminates commands
 */
public class Exit{
    /**
     * stops executing commands
     */
    public void execute(FileReader reader) {
        reader.exit();
    }
}
