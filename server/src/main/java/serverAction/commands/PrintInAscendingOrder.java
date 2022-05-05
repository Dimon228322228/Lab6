package serverAction.commands;

import action.AbstractCommand;
import action.TypeCommand;

import java.util.Set;

/**
 * output all element of the collection in ascending order
 */
public class PrintInAscendingOrder extends AbstractCommand {
    public PrintInAscendingOrder(){
        super("printInAscendingOrder",
                Set.of(TypeCommand.USER),
                "display the elements of the collection in ascending order");
    }
    /**
     * a single method for output all element of the collection by ascending order
     */
    public void execute() {
//        manager.showElements().stream().forEachOrdered(x -> System.out.println(messanger.getProductMessage(x)));
//        if (manager.showElements().isEmpty()) System.out.println("Nothing");
    }
}
