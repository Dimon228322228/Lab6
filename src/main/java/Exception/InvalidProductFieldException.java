package Exception;

public class InvalidProductFieldException extends IllegalArgumentException{
    public InvalidProductFieldException(){
        super("Invalid field of Product has been entered!");
    }
    public InvalidProductFieldException(String str){
        super(str);
    }
}
