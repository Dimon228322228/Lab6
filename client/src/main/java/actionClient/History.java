package actionClient;

import action.TypeCommand;

import java.util.Set;

/**
 * History of commands executed
 */
public class History extends AbstractCommandClient {
    public History(CommandHandlerClient comHandl){
        super("history",
                Set.of(TypeCommand.USER),
                "print the last 13 commands (without their arguments)");
        this.comHandl = comHandl;
    }
    /**
     * print history of commands executed
     */
    public String execute() {
         return comHandl.getHistory();
    }
}
