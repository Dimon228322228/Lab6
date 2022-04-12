package Exception;

public class InvalidUnitOfMeasureException extends InvalidProductFieldException{
    public InvalidUnitOfMeasureException(){
        super("Invalid value has been entered. Choose unit of measurement include list!");
    }
    public InvalidUnitOfMeasureException(String str){
        super(str);
    }
}
