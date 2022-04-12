package Exception;

public class InvalidXcoordinateException extends InvalidProductFieldException{
    public InvalidXcoordinateException(){
        super("Invalid X coordinate has been entered. This value should be lowed 938 and not null!");
    }
}
