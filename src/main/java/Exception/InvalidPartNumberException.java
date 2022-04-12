package Exception;

public class InvalidPartNumberException extends InvalidProductFieldException{
    public InvalidPartNumberException(){
        super("Invalid part number has been entered. Part number is not null and his length is grated 21");
    }
}
