package actionClient;

import action.TypeCommand;

import java.util.Set;

/**
 * used to describe the actions of the commands
 */
public class Help  extends AbstractCommandClient {
    public Help(CommandHandlerClient comHandl){
        super("help",
                Set.of(TypeCommand.USER),
                "displays reference about all commands");
        this.comHandl = comHandl;
    }
    /**
     * outputs a description of the commands
     */
    public String execute() {
        StringBuilder builder = new StringBuilder();
        builder.append("Client command: ")
                .append(System.lineSeparator());
        comHandl.getCurrentCommandData()
                .forEach(x -> builder.append(x.getName())
                                     .append(" : ")
                                     .append(x.getDescription())
                                     .append(System.lineSeparator()));
        builder.append("Server command: ")
                .append(System.lineSeparator());
        comHandl.getServerCommandsData()
                .forEach(x -> builder.append(x.getName())
                                     .append(" : ")
                                     .append(x.getDescription())
                                     .append(System.lineSeparator()));
        builder.append("NB! \"(argument)\" must be entered in in the same line as the command. Null it is empty string.")
                .append(System.lineSeparator());
        return builder.toString();
    }
}
