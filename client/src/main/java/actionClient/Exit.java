package actionClient;

import action.TypeCommand;

import java.util.Set;

/**
 * terminates commands
 */
public class Exit extends AbstractCommandClient {
    public Exit(CommandHandlerClient comHandl){
        super("exit",
                Set.of(TypeCommand.USER),
                "terminate program (without saving to file)");
        this.comHandl = comHandl;
    }
    /**
     * stops executing commands
     */
    public String execute() {
        return "exit";
    }
}
