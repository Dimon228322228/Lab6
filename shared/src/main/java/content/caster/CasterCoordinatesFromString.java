package content.caster;

import content.validator.ValidatorCoordinates;
import exceptions.InvalidProductFieldException;

/**
 * A class which creates field of coordinates from different classes
 */
public class CasterCoordinatesFromString {
    /**
     * checker correctness of field
     */
    ValidatorCoordinates valCoord = new ValidatorCoordinates();

    /**
     * @return value integer from string coordinate x
     */
    public Integer castX(String str){
        Integer x = Integer.parseInt(str);
        if (valCoord.xCoordinateValid(x)){
            return x;
        } else {
            throw new InvalidProductFieldException("Invalid X coordinate has been entered. This value should be lowed 938 and not null!");
        }
    }

    /**
     * @return value integer from string coordinate y
     */
    public Integer castY(String str){
        Integer y = Integer.parseInt(str);
        if (valCoord.yCoordinateValid(y)){
            return y;
        } else {
            throw new InvalidProductFieldException("Invalid Y coordinate has been entered. This value shouldn't be null!");
        }
    }
}
