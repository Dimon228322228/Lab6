package Content.Caster;

import Content.Validator.ValidatorCoordinates;
import Exception.InvalidXcoordinateException;
import Exception.InvalidYcoordinateException;

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
            throw new InvalidXcoordinateException();
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
            throw new InvalidYcoordinateException();
        }
    }
}
