package Exception;

public class InvalidManufactureCostException extends InvalidProductFieldException{
    public InvalidManufactureCostException(){
        super("Invalid manufacture cost has been entered!");
    }
}
