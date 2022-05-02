package Content;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
     * set creation date and set unique id
     */
    public void setAutomaticGenerateField(){
        setCreationDate(new Date());
        setId(GeneratedID.getID());
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

    static class GeneratedID {
        private static final Set<Long> idSet = new HashSet<>();

        /**
         * Get id which don't exist in set
         * @return id
         */
        public static long getID() {
            long id = 0;
            do{
                id ++;
            } while (idExists(id));
            idSet.add(id);
            return id;
        }

        /**
         * does an id exist in a set
         * @return true if exist
         */
        public static boolean idExists(Long id){
            return idSet.contains(id);
        }

        /**
         * Remove an id from the set
         */
        public static void removeID(long id) {
            idSet.remove(id);
        }

        public static void setID(long id){idSet.add(id);}

        public static void clearIdSet(){idSet.clear();}
    }
}