package serverAction;

import content.Product;
import lombok.Getter;
import lombok.Setter;
import manager.CollectionManager;
import manager.database.DatabaseManager;

public class ExecutionResources {
    @Getter CollectionManager collectionManager;
    @Getter @Setter String arg;
    @Getter @Setter Product product;
    @Getter @Setter DatabaseManager databaseManager;
    public ExecutionResources(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
}
