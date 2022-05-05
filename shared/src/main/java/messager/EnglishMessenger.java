package messager;

import content.UnitOfMeasure;

/**
 * this class stores messages
 */
public class EnglishMessenger extends AbstractMessenger {
    private static EnglishMessenger instance = null;
    private EnglishMessenger(){
        setCommandsExplanation();
        setProductFieldExplanation();
    }

    public static EnglishMessenger getInstance(){
        if (instance == null) return new EnglishMessenger();
        return instance;
    }

    /**
     * @return instructions for entering commands
     */
    @Override
    protected String getCommandMassageEnding() {
        return System.lineSeparator() +
                "NB! \"(argument)\" must be entered in in the same line as the command. Null it is empty string."
                + System.lineSeparator();
    }

    /**
     * set commands descriptions
     */
    @Override
    protected void setCommandsExplanation() {
        commands.put("help", "displays reference about all commands");
        commands.put("info", "displays information about collection");
        commands.put("show", "print all elements of the collection in string representation");
        commands.put("add", "add a new {Product} to the collection");
        commands.put("updateId {id}", "update the value of the collection element whose id is equal to the given one");
        commands.put("removeById {id}", "remove element from collection by its id");
        commands.put("clear", "clear the collection");
        commands.put("save", "save collection to file");
        commands.put("executeScript {script file}", "read and execute the script from the specified file");
        commands.put("exit", "terminate program (without saving to file)");
        commands.put("addIfMax", "add a new element to the collection if its value is greater than the value of the largest element in this collection");
        commands.put("removeLower", "remove all elements from the collection that are smaller than the given one");
        commands.put("history", "print the last 13 commands (without their arguments)");
        commands.put("countByManufactureCost {manufacture cost}", "display the number of elements whose value of the manufactureCost field is equal to the specified one");
        commands.put("countGreaterThanUnitOfMeasure {unit product}", "display the number of elements whose unitOfMeasure field value is greater than the given one");
        commands.put("printAscending", "display the elements of the collection in ascending order");
    }

    /**
     * set product fields descriptions
     */
    @Override
    protected void setProductFieldExplanation() {
        explanations.put("id", "ID");
        explanations.put("name", "Product name");
        explanations.put("x", "X coordinate");
        explanations.put("y", "Y coordinate");
        explanations.put("date", "Creation date");
        explanations.put("price", "Price");
        explanations.put("partNumber", "Part number");
        explanations.put("manufactureCost", "Manufacture cost");
        explanations.put("unitOfMeasure", "Unit of measurement");
        explanations.put("namePerson", "Owner name");
        explanations.put("birthday", "Owner birthday");
        explanations.put("height", "Owner height");
        explanations.put("weight", "Owner weight");
        explanations.put("passportId", "Owner passport id");
    }


    /**
     * @return an invitation to enter simple fields
     */
    public String getFieldInvitationMessage(String nameField){
        return "Enter " + explanations.get(nameField).toLowerCase() + ": ";
    }

    /**
     * @return prompt to enter a unit of measure
     */
    @Override
    public String getUnitOfMeasureInputInvitationMessage() {
        return "Choose value from list: " + UnitOfMeasure.getTitleInColumn() +  "And enter product unit of measurement: ";
    }

    /**
     * @return prompt to enter an owner birthday
     */
    @Override
    public String getPersonBirthdayInputInvitationMessage() {
        return "Enter owner birthday with separated - '-' (Year-Month-Day): ";
    }
}
