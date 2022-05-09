package content;

import content.caster.CasterCoordinatesFromString;
import content.caster.CasterFieldProductFromString;
import content.caster.CasterPersonFromString;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class BuilderProduct {
    private final Product product;
    private final Coordinates coordinates;
    private Person owner = null;
    private final CasterPersonFromString casterPersonFromString = new CasterPersonFromString();
    private final CasterFieldProductFromString casterFieldProductFromString = new CasterFieldProductFromString();
    private final CasterCoordinatesFromString casterCoordinatesFromString = new CasterCoordinatesFromString();
    private final Map<Consumer<String>, String> invitation = new HashMap<>();

    public BuilderProduct(){
        product = new Product();
        coordinates = new Coordinates();
        initialInvitation();
    }

    private Person getOwner(){
        if(owner == null) {owner = new Person();}
        return owner;
    }

    /**
     * set name product from string
     */
    public BuilderProduct setName(String name){
        product.setName(casterFieldProductFromString.castName(name));
        return this;
    }

    /**
     * set x coordinate from string
     */
    public BuilderProduct setXCoordinate(String x){coordinates.setX(casterCoordinatesFromString.castX(x));return this;}
    /**
     * set y coordinate from string
     */
    public BuilderProduct setYCoordinate(String y){coordinates.setY(casterCoordinatesFromString.castY(y));return this;}
    /**
     * set price product from string
     */
    public BuilderProduct setPrice(String price){product.setPrice(casterFieldProductFromString.castPrice(price));return this;}
    /**
     * set part number product from string
     */
    public BuilderProduct setPartNumber(String partNumber){product.setPartNumber(casterFieldProductFromString.castPartNumber(partNumber));return this;}
    /**
     * set unit product from string
     */
    public BuilderProduct setUnitOfMeasure(String unit){product.setUnitOfMeasure(casterFieldProductFromString.castUnitOfMeasure(unit));return this;}
    /**
     * set manufacture cost product from string
     */
    public BuilderProduct setManufactureCost(String manufactureCost){product.setManufactureCost(casterFieldProductFromString.castManufactureCost(manufactureCost));return this;}
    /**
     * set name owner from string
     */
    public BuilderProduct setPersonName(String name){getOwner().setName(casterPersonFromString.castName(name));return this;}
    /**
     * set birthday owner from string
     */
    public BuilderProduct setPersonBirthday(String time){getOwner().setBirthday(casterPersonFromString.castBirthday(time));return this;}
    /**
     * set height owner from string
     */
    public BuilderProduct setPersonHeight(String height){getOwner().setHeight(casterPersonFromString.castHeight(height));return this;}
    /**
     * set weight owner from string
     */
    public BuilderProduct setPersonWeight(String weight){getOwner().setWeight(casterPersonFromString.castWeight(weight));return this;}
    /**
     * set passport id from string
     */
    public BuilderProduct setPersonPassportId(String passportId){getOwner().setPassportID(casterPersonFromString.castPassportID(passportId));return this;}

    public Product getProduct(){
        product.setCoordinates(coordinates);
        product.setOwner(owner);
        return product;
    }

    public void initialInvitation(){
        invitation.put(this::setName, "Enter name product: ");
        invitation.put(this::setXCoordinate, "Enter x coordinate: ");
        invitation.put(this::setYCoordinate, "Enter y coordinate");
        invitation.put(this::setPrice, "Enter price: ");
        invitation.put(this::setPartNumber, "Enter part number: ");
        invitation.put(this::setManufactureCost, "Enter manufacture cost: ");
        invitation.put(this::setUnitOfMeasure, "Choose value from list: " + UnitOfMeasure.getTitleInColumn() + " And enter product unit of measurement: ");
        invitation.put(this::setPersonName, "Enter owner name: ");
        invitation.put(this::setPersonBirthday, "Enter owner birthday with separated - '-' (Year-Month-Day): ");
        invitation.put(this::setPersonHeight, "Enter owner height: ");
        invitation.put(this::setPersonWeight, "Enter person weight: ");
        invitation.put(this::setPersonPassportId, "Enter owner passport id: ");
    }

    /**
     * @return an invitation to enter simple fields
     */
    public String getInvitation(Consumer<String> setter){
        return invitation.get(setter);
    }
}
