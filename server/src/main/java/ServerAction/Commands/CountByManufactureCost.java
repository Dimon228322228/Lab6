package ServerAction.Commands;

import Action.Command;
import Action.TypeCommand;
import Manager.CollectionManager;
import Messager.Messenger;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * count number of element equals by manufacture cost
 */
public class CountByManufactureCost implements Command {
    @Getter final Set<TypeCommand> type = new HashSet<>();
    @Getter final String name = "countByManufactureCost";
    public CountByManufactureCost(){
        type.add(TypeCommand.USER);
        type.add(TypeCommand.ARG);
    }
    /**
     * read manufacture cost
     * checked it is corrected
     * count number of element
     * print result
     */
    public void execute(CollectionManager manager, String arg, Messenger messanger) {
        double manufactureCost;
        try{
            manufactureCost = Double.parseDouble(arg);
        } catch (NumberFormatException | NullPointerException e){
            System.err.println("Manufacture cost must be convert to double.");
            return;
        }
        System.out.println(messanger.getCountElementWithCondition(manager.countByManufactureCost(manufactureCost)));
    }
}
