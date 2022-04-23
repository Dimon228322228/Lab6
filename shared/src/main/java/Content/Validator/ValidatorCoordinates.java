package Content.Validator;

/**
 * a class check each coordinate
 */
public class ValidatorCoordinates {

    /**
     * @return true if x lower than 938 and not null
     */
    public boolean xCoordinateValid(Integer x){
        return x != null && x <= 938;
    }

    /**
     * @return true if y not null
     */
    public boolean yCoordinateValid(Integer y){
        return y != null;
    }

}
