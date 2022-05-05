package messager;

import content.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * this class stores messages
 */
public abstract class AbstractMessenger implements Messenger {
    /**
     * HashMap with explanations of field
     */
    protected Map<String, String> explanations = new HashMap<>();
    /**
     * HashMap with explanations of commands
     */
    protected Map<String, String> commands = new HashMap<>();


    /**
     * @return description of one command
     */
    protected String getCommandMessage(String command){
        return command + ": " + commands.get(command) + "\n";
    }

    /**
     * @return description of all commands
     */
    @Override
    public String getCommandsMessage(){
        return commands.keySet()
                .stream()
                .map(this::getCommandMessage)
                .collect(Collectors.joining(System.lineSeparator(), "", getCommandMassageEnding()));
    }

    /**
     * @return instructions for entering commands
     */
    protected abstract String getCommandMassageEnding();

    /**
     * set commands descriptions
     */
    protected abstract void setCommandsExplanation();

    /**
     * set product fields descriptions
     */
    protected abstract void setProductFieldExplanation();

    /**
     * @return response to queries with condition
     */
    public String getCountElementWithCondition(Long value){
        return "Found " + value + " elements of the collection." ;
    }
}
