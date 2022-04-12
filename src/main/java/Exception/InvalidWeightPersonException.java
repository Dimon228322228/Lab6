package Exception;

public class InvalidWeightPersonException extends InvalidProductFieldException{
    public InvalidWeightPersonException(){
        super("Invalid weight has been entered. Value have to grated 0!");
    }
}
