package Exception;

public class InvalidBirthdayPersonException extends InvalidProductFieldException{
    public InvalidBirthdayPersonException(){
        super("Invalid birthday date has been entered. Value isn't being null!");
    }
}
