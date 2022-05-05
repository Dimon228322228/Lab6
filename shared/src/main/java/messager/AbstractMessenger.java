package messager;

import content.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * this class stores messages
 */
public abstract class AbstractMessenger implements Messenger {
    /**
     * HashMap with explanations of field
     */
    protected Map<String, String> explanations = new HashMap<>();
    /**
     * HashMap with explanations of commands
     */
    protected Map<String, String> commands = new HashMap<>();

    /**
     * @return product field information
     */
    protected String getFieldMassage(String field, Object value){
        return explanations.get(field) + ": " + value + "\n";
    }

    /**
     * Used to get information about each field
     * @return Full product description
     */
    @Override
    public String getProductMessage(Product product){
        String message = "";
        message += getFieldMassage("id", product.getId());
        message += getFieldMassage("name", product.getName());
        message += getFieldMassage("x", product.getCoordinates().getX());
        message += getFieldMassage("y", product.getCoordinates().getY());
        message += getFieldMassage("date", product.getCreationDate());
        message += getFieldMassage("price", product.getPrice());
        message += getFieldMassage("partNumber", product.getPartNumber());
        message += getFieldMassage("manufactureCost", product.getManufactureCost());
        message += getFieldMassage("unitOfMeasure", product.getUnitOfMeasure());
        if (product.getOwner() != null) {
            message += getFieldMassage("namePerson", product.getOwner().getName());
            message += getFieldMassage("birthday", product.getOwner().getBirthday().toString());
            message += getFieldMassage("height", product.getOwner().getHeight());
            message += getFieldMassage("weight", product.getOwner().getWeight());
            message += getFieldMassage("passportId", product.getOwner().getPassportID());
        }else{
            message += "Owner: " + product.getOwner() + "\n";
        }
        return message;
    }

    /**
     * @return description of one command
     */
    protected String getCommandMessage(String command){
        return command + ": " + commands.get(command) + "\n";
    }

    /**
     * @return description of all commands
     */
    @Override
    public String getCommandsMessage(){
        return commands.keySet()
                .stream()
                .map(this::getCommandMessage)
                .collect(Collectors.joining(System.lineSeparator(), "", getCommandMassageEnding()));
    }

    /**
     * @return instructions for entering commands
     */
    protected abstract String getCommandMassageEnding();

    /**
     * set commands descriptions
     */
    protected abstract void setCommandsExplanation();

    /**
     * set product fields descriptions
     */
    protected abstract void setProductFieldExplanation();

    /**
     * @return response to queries with condition
     */
    public String getCountElementWithCondition(Long value){
        return "Found " + value + " elements of the collection." ;
    }
}
