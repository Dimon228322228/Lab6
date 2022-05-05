package content.caster;

import content.UnitOfMeasure;
import content.validator.ValidatorProduct;
import exceptions.InvalidProductFieldException;

/**
 * A class which creates field of product from different classes
 */
public class CasterFieldProductFromString {
     /**
      * checker correctness of field
      */
     ValidatorProduct valProd = new ValidatorProduct();

     /**
      * @return checked name from the string
      */
     public String castName(String name){
          if (valProd.nameProductValid(name.trim())) {
               return name.trim();
          } else throw new InvalidProductFieldException("Name don't have to empty or null!");
     }

     /**
      * @return checked price from the string
      */
     public Double castPrice(String str){
          Double price = Double.parseDouble(str.trim());
          if(valProd.priceValid(price)){
               return price;
          } else {
               throw new InvalidProductFieldException("Invalid price has been entered. Price should be grated 0 and not null");
          }
     }

     /**
      * @return checked part number from the string
      */
     public String castPartNumber(String str){
          if (str.trim().equals("")) return null;
          if (valProd.partNumberValid(str.trim())){
               return str.trim();
          } else {
               throw new InvalidProductFieldException("Invalid part number has been entered. Part number is not null and his length is grated 21");
          }
     }

     /**
      * @return checked manufacture cost from the string
      */
     public double castManufactureCost(String str){
          double manufactureCost = Double.parseDouble(str.trim());
          if(valProd.manufactureCostValid(manufactureCost)){
               return manufactureCost;
          } else {
               throw new InvalidProductFieldException("Invalid manufacture cost has been entered!");
          }
     }

     /**
      * @return checked unit product from the string
      */
     public UnitOfMeasure castUnitOfMeasure(String str){
          if (str.trim().equals("")) return null;
          if (valProd.unitOfMeasureValid(str)){
               return UnitOfMeasure.fromString(str.trim());
          } else {
               throw new InvalidProductFieldException("Invalid value has been entered. Choose unit of measurement include list!");
          }
     }
}
