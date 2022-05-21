package serverAction.commands;

import action.ResultAction;
import action.State;
import exceptions.EmptyFileException;
import serverAction.AbstractCommandServer;
import serverAction.ExecutionResources;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Set;

/**
 * a class for saving all element of the collection
 */
public class Save extends AbstractCommandServer {
    public Save(ExecutionResources executionResources){
        super("save",
                Set.of(),
                "save collection to file");
        this.executionResources = executionResources;
    }
    /**
     * a single method for saving collection in file (format XML)
     */
    public ResultAction execute() {
//        try {
            executionResources.getCollectionManager().save();
//        } catch (JAXBException | EmptyFileException | IOException | InvalidPathException | NullPointerException e) {
//            return new ResultAction(State.ERROR, "Can't save the collection. \n");
//        }
        return new ResultAction(State.SUCCESS, "The collection has been saved. \n");
    }
}
