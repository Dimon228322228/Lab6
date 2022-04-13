package Command;

import Command.CommandFactory.CommandFactory;
import Command.Reader.Reader;
import Manager.CollectionManager;
import Messager.Messenger;

/**
 * count number of element equals by manufacture cost
 */
public class CountByManufactureCost implements Command{
    /**
     * read manufacture cost
     * checked it is corrected
     * count number of element
     * print result
     */
    @Override
    public void execute(CollectionManager manager, Reader reader, String arg, Messenger messanger, CommandFactory commandFactory) {
        double manufactureCost;
        try{
            manufactureCost = Double.parseDouble(arg);
        } catch (NumberFormatException | NullPointerException e){
            System.err.println("Manufacture cost must be convert to double.");
            return;
        }
        long count = manager.countByManufactureCost(manufactureCost);
        System.out.println(messanger.getCountElementWithCondition(count));
    }
}
