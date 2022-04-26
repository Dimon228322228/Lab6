package ServerAction;

import Action.Command;
import Action.CommandManager;
import Action.TypeCommand;
import Exceptions.UnknownCommandException;
import ServerAction.CommandFactory.CommandFactory;
import ServerAction.CommandFactory.CommandFactoryImpl;

import java.util.List;

public class CommandManag implements CommandManager {
    CommandFactory commandFactory = CommandFactoryImpl.getInstance();
    List<Command> commands = commandFactory.getCommands();

    @Override
    public boolean isArg(String str) {
        Command command;
        if (isCommandPresent(str)) command = commandFactory.getCommand(str);
        else throw new UnknownCommandException();
        return command.getType().contains(TypeCommand.ARG);
    }

    @Override
    public boolean isProduct(String str) {
        Command command;
        if (isCommandPresent(str)) command = commandFactory.getCommand(str);
        else throw new UnknownCommandException();
        return command.getType().contains(TypeCommand.PRODUCT);
    }

    @Override
    public boolean isExecuted(String str) {
        Command command;
        if (isCommandPresent(str)) command = commandFactory.getCommand(str);
        else throw new UnknownCommandException();
        return command.getType().contains(TypeCommand.EXECUTED);
    }

    private boolean isCommandPresent(String str) {
        return commands.stream()
                .map(Command::getName)
                .anyMatch(x -> x.equals(str));
    }

}
