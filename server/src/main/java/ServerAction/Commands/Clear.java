package ServerAction.Commands;

import Manager.CollectionManager;

/**
 * Clearing collection
 */
public class Clear{
    /**
     * A single method which clear collection
     */
    public void execute(CollectionManager manager) {
        manager.clear();
        System.out.println("Clearing success");
    }
}
