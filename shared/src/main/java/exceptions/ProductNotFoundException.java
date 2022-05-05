package exceptions;

import java.util.NoSuchElementException;

public class ProductNotFoundException extends NoSuchElementException {
    public ProductNotFoundException(){
        super("Element with this id not found in the collection!");
    }
}
