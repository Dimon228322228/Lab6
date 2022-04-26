package Exceptions;

public class InvalidProductFieldException extends IllegalArgumentException{
    public InvalidProductFieldException(String str){
        super(str);
    }
}
