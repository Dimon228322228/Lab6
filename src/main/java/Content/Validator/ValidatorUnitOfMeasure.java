package Content.Validator;

import Content.UnitOfMeasure;

/**
 * a class for check correctness product unit
 */
public class ValidatorUnitOfMeasure {
    /**
     * for input from file(if not contains list of the UnitOfMeasure, then parser set null and this work correctness)
     * @return true if unit product not null
     */
    public boolean validUnitOfMeasure(UnitOfMeasure unitOfMeasure){
        return true;
    }

    /**
     * for input from console
     * @return true if unit product entered correctness
     */
    public boolean validUnitOfMeasure(String unitOfMeasure){
        if (unitOfMeasure == null) return false;
        String[] list = UnitOfMeasure.getTitleInString().trim().split(" ");
        for (String string: list){
            if(unitOfMeasure.equals(string)) return true;
        }
        return false;
    }

}
