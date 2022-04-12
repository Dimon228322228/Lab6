package Exception;

public class InvalidPriceException extends InvalidProductFieldException{
    public InvalidPriceException(){
        super("Invalid price has been entered. Price should be grated 0 and not null");
    }
}
