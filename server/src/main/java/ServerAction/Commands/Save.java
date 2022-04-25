package ServerAction.Commands;

import Manager.CollectionManager;

/**
 * a class for saving all element of the collection
 */
public class Save{
    /**
     * a single method for saving collection in file (format XML)
     */
    public void execute(CollectionManager manager) {
        manager.save();
    }
}
