package Content;

import Content.Caster.CasterCoordinatesFromString;
import Content.Caster.CasterFieldProductFromString;
import Content.Caster.CasterPersonFromString;

public class BuilderProduct {
    private final Product product;
    private final Coordinates coordinates;
    private Person owner = null;
    private final CasterPersonFromString casterPersonFromString = new CasterPersonFromString();
    private final CasterFieldProductFromString casterFieldProductFromString = new CasterFieldProductFromString();
    private final CasterCoordinatesFromString casterCoordinatesFromString = new CasterCoordinatesFromString();

    public BuilderProduct(){
        product = new Product();
        coordinates = new Coordinates();
    }

    private Person getOwner(){
        if(owner == null) {owner = new Person();}
        return owner;
    }
    /**
     * set name product from string
     */
    public void setName(String name){product.setName(casterFieldProductFromString.castName(name));}
    /**
     * set x coordinate from string
     */
    public void setXCoordinate(String x){coordinates.setX(casterCoordinatesFromString.castX(x));}
    /**
     * set y coordinate from string
     */
    public void setYCoordinate(String y){coordinates.setY(casterCoordinatesFromString.castY(y));}
    /**
     * set price product from string
     */
    public void setPrice(String price){product.setPrice(casterFieldProductFromString.castPrice(price));}
    /**
     * set part number product from string
     */
    public void setPartNumber(String partNumber){product.setPartNumber(casterFieldProductFromString.castPartNumber(partNumber));}
    /**
     * set unit product from string
     */
    public void setUnitOfMeasure(String unit){product.setUnitOfMeasure(casterFieldProductFromString.castUnitOfMeasure(unit));}
    /**
     * set manufacture cost product from string
     */
    public void setManufactureCost(String manufactureCost){product.setManufactureCost(casterFieldProductFromString.castManufactureCost(manufactureCost));}
    /**
     * set name owner from string
     */
    public void setPersonName(String name){getOwner().setName(casterPersonFromString.castName(name));}
    /**
     * set birthday owner from string
     */
    public void setPersonBirthday(String time){getOwner().setBirthday(casterPersonFromString.castBirthday(time));}
    /**
     * set height owner from string
     */
    public void setPersonHeight(String height){getOwner().setHeight(casterPersonFromString.castHeight(height));}
    /**
     * set weight owner from string
     */
    public void setPersonWeight(String weight){getOwner().setWeight(casterPersonFromString.castWeight(weight));}
    /**
     * set passport id from string
     */
    public void setPersonPassportId(String passportId){getOwner().setPassportID(casterPersonFromString.castPassportID(passportId));}

    /**
     * set name product from string
     */
    public BuilderProduct setNameChain(String name){
        product.setName(casterFieldProductFromString.castName(name));
        return this;
    }
    /**
     * set x coordinate from string
     */
    public BuilderProduct setXCoordinateChain(String x){coordinates.setX(casterCoordinatesFromString.castX(x));return this;}
    /**
     * set y coordinate from string
     */
    public BuilderProduct setYCoordinateChain(String y){coordinates.setY(casterCoordinatesFromString.castY(y));return this;}
    /**
     * set price product from string
     */
    public BuilderProduct setPriceChain(String price){product.setPrice(casterFieldProductFromString.castPrice(price));return this;}
    /**
     * set part number product from string
     */
    public BuilderProduct setPartNumberChain(String partNumber){product.setPartNumber(casterFieldProductFromString.castPartNumber(partNumber));return this;}
    /**
     * set unit product from string
     */
    public BuilderProduct setUnitOfMeasureChain(String unit){product.setUnitOfMeasure(casterFieldProductFromString.castUnitOfMeasure(unit));return this;}
    /**
     * set manufacture cost product from string
     */
    public BuilderProduct setManufactureCostChain(String manufactureCost){product.setManufactureCost(casterFieldProductFromString.castManufactureCost(manufactureCost));return this;}
    /**
     * set name owner from string
     */
    public BuilderProduct setPersonNameChain(String name){getOwner().setName(casterPersonFromString.castName(name));return this;}
    /**
     * set birthday owner from string
     */
    public BuilderProduct setPersonBirthdayChain(String time){getOwner().setBirthday(casterPersonFromString.castBirthday(time));return this;}
    /**
     * set height owner from string
     */
    public BuilderProduct setPersonHeightChain(String height){getOwner().setHeight(casterPersonFromString.castHeight(height));return this;}
    /**
     * set weight owner from string
     */
    public BuilderProduct setPersonWeightChain(String weight){getOwner().setWeight(casterPersonFromString.castWeight(weight));return this;}
    /**
     * set passport id from string
     */
    public BuilderProduct setPersonPassportIdChain(String passportId){getOwner().setPassportID(casterPersonFromString.castPassportID(passportId));return this;}

    public Product getProduct(){
        product.setCoordinates(coordinates);
        product.setOwner(owner);
        return product;
    }
}
