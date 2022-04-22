package Messager;

import Content.Product;

/**
 * this class stores messages
 */
public interface Messenger {
    /**
     * Used to get information about each field
     * @return Full product description
     */
    String getProductMessage(Product product);

    /**
     * @return description of all commands
     */
    String getCommandsMessage();

    /**
     * @return collection information
     */
    String getCollectionMessage(String type, String size, String creationDate);

    /**
     * @return prompt to enter a unit of measure
     */
    String getUnitOfMeasureInputInvitationMessage();

    /**
     * @return prompt to enter a owner birthday
     */
    String getPersonBirthdayInputInvitationMessage();

    /**
     * @return responses to queries with condition
     */
    String getCountElementWithCondition(Long value);

    /**
     * @return an invitation to enter simple fields
     */
    String getFieldInvitationMessage(String value);
}
