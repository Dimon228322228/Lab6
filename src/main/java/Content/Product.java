package Content;

import Content.Caster.CasterFieldProductFromString;
import Manager.QueueManager;
import lombok.Getter;
import lombok.Setter;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * a class of product
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Product implements Serializable, Comparable<Product> {
    @Getter @Setter private long id; // >0 , unique and automatic generated
    @Getter @Setter private String name ; // not null and not empty
    @Getter @Setter private Coordinates coordinates ; // not null
    @XmlJavaTypeAdapter(value = DateSerializer.class) @Getter @Setter private Date creationDate ; // not null and automatic generation
    @Getter @Setter private Double price ; // not null and >0
    @Getter @Setter private String partNumber ; // >=22 and maybe null
    @Getter @Setter private double manufactureCost ; //
    @XmlJavaTypeAdapter(value = UnitSerializer.class) @Getter @Setter private UnitOfMeasure unitOfMeasure ; // maybe null
    @Getter @Setter private Person owner ; // maybe null

    /**
     * creates fields of different classes and checks their correctness
     */
    @XmlTransient private final CasterFieldProductFromString casterFieldProductFromString = new CasterFieldProductFromString();

    /**
     * set creation date and set unique id
     */
    public void setAutomaticGenerateField(){
        setCreationDate(new Date());
        setId(QueueManager.getID());
    }

    /**
     * set name product from string, used in ConsoleReader
     */
    public void setNameStr(String inputStr){
        setName(casterFieldProductFromString.castName(inputStr));
    }

    /**
     * set price product from string, used in ConsoleReader
     */
    public void setPriceStr(String inputStr){
        setPrice(casterFieldProductFromString.castPrice(inputStr));
    }

    /**
     * set part number product from string, used in ConsoleReader
     */
    public void setPartNumberStr(String inputStr){
        setPartNumber(casterFieldProductFromString.castPartNumber(inputStr));
    }

    /**
     * set manufacture cost product from string, used in ConsoleReader
     */
    public void setManufactureCostStr(String inputStr){
        setManufactureCost(casterFieldProductFromString.castManufactureCost(inputStr));
    }

    /**
     * set unit product from string, used in ConsoleReader
     */
    public void setUnitOfMeasureStr(String inputStr){
        setUnitOfMeasure(casterFieldProductFromString.castUnitOfMeasure(inputStr));
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Product product)) return false;
        if (this == o) return true;
        return product.getId() == this.getId();
    }

    @Override
    public int hashCode(){
        return (int) this.getId();
    }

    @Override
    public String toString(){
        return String.format("ID = %d, Name = %s, %s, Creation Date = %s, Price = %f, PartNumber = %s, Manufacture cost = %f, Unit of Measurement = %s,Owner: %s",
                id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner);
    }

    public int compareTo(Product o) {
        return this.getName().compareTo(o.getName());
    }
}