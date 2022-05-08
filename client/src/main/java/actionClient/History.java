package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;

import java.util.Set;

/**
 * History of commands executed
 */
public class History extends AbstractCommandClient {
    public History(CommandController comHandl){
        super("history",
                Set.of(TypeCommand.USER),
                "print the last 13 commands (without their arguments)");
        this.comContr = comHandl;
    }
    /**
     * print history of commands executed
     */
    public ResultAction execute() {
         return new ResultAction(State.SUCCESS, comContr.getHistory());
    }
}
