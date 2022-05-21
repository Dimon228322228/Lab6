package content;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * a class of product
 */
public class Product implements Serializable, Comparable<Product> {
    @Getter @Setter private long id; // >0 , unique and automatic generated
    @Getter @Setter private String name ; // not null and not empty
    @Getter @Setter private Coordinates coordinates ; // not null
    @Getter @Setter private Date creationDate ; // not null and automatic generation
    @Getter @Setter private Double price ; // not null and >0
    @Getter @Setter private String partNumber ; // >=22 and maybe null
    @Getter @Setter private double manufactureCost ; //
    @Getter @Setter private UnitOfMeasure unitOfMeasure ; // maybe null
    @Getter @Setter private Person owner ; // maybe null

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
        StringBuilder builder = new StringBuilder();
        builder.append("ID : ").append(getId()).append("\n")
                .append("Product name : ").append(getName()).append("\n")
                .append("X coordinate : ").append(getCoordinates().getX()).append("\n")
                .append("Y coordinate : ").append(getCoordinates().getY()).append("\n")
                .append("Creation date : ").append(getCreationDate()).append("\n")
                .append("Price : ").append(getPrice()).append("\n")
                .append("Part number : ").append(getPartNumber()).append("\n")
                .append("Manufacture cost : ").append(getManufactureCost()).append("\n")
                .append("Unit of measurement : ").append(getUnitOfMeasure()).append("\n");
            if (getOwner() != null) {
                builder.append("Owner name : ").append(getOwner().getName()).append("\n")
                        .append("Owner birthday : ").append(getOwner().getBirthday().toString()).append("\n")
                        .append("Owner height : ").append(getOwner().getHeight()).append("\n")
                        .append("Owner weight : ").append(getOwner().getWeight()).append("\n")
                        .append("Owner passport id : ").append(getOwner().getPassportID()).append("\n");
            }else{
                builder.append("Owner: ").append(getOwner()).append("\n");
            }
            return builder.append("\n").toString();
    }

    public int compareTo(Product o) {
        return this.getName().compareTo(o.getName());
    }

}