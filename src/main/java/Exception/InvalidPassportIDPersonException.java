package Exception;

public class InvalidPassportIDPersonException extends InvalidProductFieldException{
    public InvalidPassportIDPersonException(){
        super("Invalid passport id has been entered. Value isn't null, it is lowered 41 and grated 6!");
    }
}
