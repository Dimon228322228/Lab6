package ServerAction.Commands;

import Manager.CollectionManager;
import Messager.Messenger;

/**
 * count number of element equals by manufacture cost
 */
public class CountByManufactureCost{
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
