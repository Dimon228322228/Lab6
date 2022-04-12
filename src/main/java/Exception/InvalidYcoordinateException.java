package Exception;

public class InvalidYcoordinateException extends InvalidProductFieldException{
    public InvalidYcoordinateException(){
        super("Invalid Y coordinate has been entered. This value shouldn't be null!");
    }
}
