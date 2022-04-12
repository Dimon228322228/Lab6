package Exception;

public class InvalidHeightPersonException extends InvalidProductFieldException{
    public InvalidHeightPersonException(){
        super("Invalid height has been entered. Value have to grated 0!");
    }
}
