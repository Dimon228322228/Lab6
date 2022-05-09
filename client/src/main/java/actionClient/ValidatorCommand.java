package actionClient;

import action.CommandData;
import action.TypeCommand;

import java.util.List;

public class ValidatorCommand {
    List<CommandData> commandsData;

    public ValidatorCommand(List<CommandData> commandsData){
        this.commandsData = commandsData;
    }

    private boolean checkedCommandByType(String commandName, TypeCommand type){
        return commandsData.stream()
                .filter(x -> x.getName().equals(commandName))
                .map(CommandData::getTypes)
                .anyMatch(x -> x.contains(type));
    }

    public boolean argPresent(String command){
        return checkedCommandByType(command, TypeCommand.ARG) ;
    }

    public boolean productPresent(String command){
        return checkedCommandByType(command, TypeCommand.PRODUCT);
    }

    public boolean isUserCommand(String command){
        return checkedCommandByType(command, TypeCommand.USER);
    }

    public boolean isCommandPresent(String command){
        return commandsData.stream().map(CommandData::getName).anyMatch(command::equals);
    }

    public boolean commandValidString(List<String> commandList){
        return commandList.size() <= 2 && commandList.size() > 0 ;
    }
}
