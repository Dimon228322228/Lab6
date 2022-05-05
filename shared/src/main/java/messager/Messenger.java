package messager;

import content.Product;

/**
 * this class stores messages
 */
public interface Messenger {

    /**
     * @return description of all commands
     */
    String getCommandsMessage();


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
