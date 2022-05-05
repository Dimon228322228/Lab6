package serverAction;

import content.Product;
import lombok.Getter;
import lombok.Setter;
import manager.CollectionManager;

public class ExecutionResources {
    @Getter CollectionManager collectionManager;
    @Getter @Setter String arg;
    @Getter @Setter Product product;
    public ExecutionResources(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
}
