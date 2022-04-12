package Exception;

public class InvalidNamePersonException extends InvalidProductFieldException{
    public InvalidNamePersonException(){
        super("Invalid name person has been entered. Name isn't being null or empty!");
    }
}
