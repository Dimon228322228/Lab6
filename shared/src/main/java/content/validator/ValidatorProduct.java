package content.validator;

import content.Coordinates;
import content.Person;
import content.UnitOfMeasure;

import java.util.Date;

/**
 * a class checked correctness fields product
 */
public class ValidatorProduct {

    private final ValidatorCoordinates validcoordinates = new ValidatorCoordinates();
    private final ValidatorUnitOfMeasure validunitofmeasure = new ValidatorUnitOfMeasure();
    private final ValidatorPerson validperson = new ValidatorPerson();

    /**
     * @return true if name not null and not empty
     */
    public boolean nameProductValid(String name){
        return(name != null && !name.trim().equals(""));
    }

    /**
     * @return true if coordinate not null and everyone correct input
     */
    public boolean coordinateValid(Coordinates coordinates){
        return (coordinates != null &&  validcoordinates.xCoordinateValid(coordinates.getX()) &&
                                        validcoordinates.yCoordinateValid(coordinates.getY()));
    }

    /**
     * @return true if date not null
     */
    public boolean dateValid(Date date){
        return date != null;
    }

    /**
     * @return true if price grater then 0 and not null
     */
    public boolean priceValid(Double price){
        return (price != null && price > 0);
    }

    /**
     * @return true if part number not null and grater then 22
     */
    public boolean partNumberValid(String partnumber){
        if (partnumber != null) return (partnumber.length() >= 22);
        else return true;
    }

    /**
     * @return true always
     */
    public boolean manufactureCostValid(double cost){
        return true;
    }

    /**
     * for parser
     * @return true if unit product has been entered correctness
     */
    public boolean unitOfMeasureValid(UnitOfMeasure unitOfMeasure){
        return validunitofmeasure.validUnitOfMeasure(unitOfMeasure);
    }

    /**
     * for console
     * @return true if unit product has been entered correctness
     */
    public boolean unitOfMeasureValid(String unitOfMeasure){
        return validunitofmeasure.validUnitOfMeasure(unitOfMeasure);
    }

    /**
     * @return true if person not null and each field has been entered correctness
     */
    public boolean personValid(Person person){
        if (person != null) {
            return (validperson.birthdayValid(person.getBirthday()) &&
                    validperson.heightValid(person.getHeight()) &&
                    validperson.namePersonValid(person.getNameOwner()) &&
                    validperson.passportidValid(person.getPassportID()) &&
                    validperson.weightValid(person.getWeight()));
        } else return true;
    }

}
