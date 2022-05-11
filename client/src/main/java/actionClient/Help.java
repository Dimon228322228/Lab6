package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;

import java.util.Set;

/**
 * used to describe the actions of the commands
 */
public class Help  extends AbstractCommandClient {
    public Help(CommandController comContr){
        super("help",
                Set.of(TypeCommand.USER),
                "displays reference about all commands");
        this.comContr = comContr;
    }
    /**
     * outputs a description of the commands
     */
    public ResultAction execute() {
        StringBuilder builder = new StringBuilder();
        builder.append(" -------------- Client command ------------- ")
                .append(System.lineSeparator());
        comContr.getCurrentCommandData()
                .forEach(x -> builder.append(x.getName())
                                     .append(" : \n----- ")
                                     .append(x.getDescription().toUpperCase())
                                     .append(System.lineSeparator()));
        builder.append(" -------------- Server command ------------- ")
                .append(System.lineSeparator());
        comContr.getServerCommandsData()
                .forEach(x -> builder.append(x.getName())
                                     .append(" : \n----- ")
                                     .append(x.getDescription().toUpperCase())
                                     .append(System.lineSeparator()));
        builder.append("NB! \"(argument)\" must be entered in in the same line as the command. Null it is empty string.")
                .append(System.lineSeparator());
        return new ResultAction(State.SUCCESS, builder.toString());
    }
}
