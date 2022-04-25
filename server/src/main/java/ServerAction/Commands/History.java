package ServerAction.Commands;

import ServerAction.CommandFactory.CommandFactory;

/**
 * History of commands executed
 */
public class History{
    /**
     * print history of commands executed
     */
    public void execute(CommandFactory commandFactory) {
         System.out.println(commandFactory.getHistory());
    }
}
