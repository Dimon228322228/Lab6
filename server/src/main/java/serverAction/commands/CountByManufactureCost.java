package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * count number of element equals by manufacture cost
 */
public class CountByManufactureCost extends AbstractCommand {
    public CountByManufactureCost(){
        super("countByManufactureCost",
                Set.of(TypeCommand.USER, TypeCommand.ARG),
                "display the number of elements whose value of the manufactureCost field is equal to the specified one");
    }
    /**
     * read manufacture cost
     * checked it is corrected
     * count number of element
     * print result
     */
    public void execute() {
        double manufactureCost;
//        try{
//            manufactureCost = Double.parseDouble(arg);
//        } catch (NumberFormatException | NullPointerException e){
//            System.err.println("Manufacture cost must be convert to double.");
//            return;
//        }
//        System.out.println(messanger.getCountElementWithCondition(manager.countByManufactureCost(manufactureCost)));
    }
}
