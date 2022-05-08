package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;

import java.util.Set;

/**
 * terminates commands
 */
public class Exit extends AbstractCommandClient {
    public Exit(CommandController comHandl){
        super("exit",
                Set.of(TypeCommand.USER),
                "terminate program (without saving to file)");
        this.comContr = comHandl;
    }
    /**
     * stops executing commands
     */
    public ResultAction execute() {
        return new ResultAction(State.EXIT, "Program has been finished. ");
    }
}
