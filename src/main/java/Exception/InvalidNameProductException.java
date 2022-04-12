package Exception;

public class InvalidNameProductException extends InvalidProductFieldException{
    public InvalidNameProductException(){
        super("Name don't have to empty or null!");
    }
}
