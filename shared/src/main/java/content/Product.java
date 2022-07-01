package content;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

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
    @Getter @Setter private String username;

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Product product)) return false;
        if (this == o) return true;
        if (product.hashCode() != this.hashCode()) return false;
        return  Objects.equals(name, product.getName()) &&
                Objects.equals(coordinates, product.getCoordinates()) &&
                Objects.equals(creationDate, product.getCreationDate()) &&
                Objects.equals(price, product.getPrice()) &&
                Objects.equals(partNumber, product.getPartNumber()) &&
                Objects.equals(manufactureCost, product.getManufactureCost()) &&
                Objects.equals(unitOfMeasure, product.getUnitOfMeasure()) &&
                Objects.equals(owner, product.getOwner()) &&
                Objects.equals(username, product.getUsername());
    }

    @Override
    public int hashCode(){
        return (int) this.getId();
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("ID : ").append(getId()).append("\n\t")
                .append("Product name : ").append(getName()).append("\n\t")
                .append("X coordinate : ").append(getCoordinates().getX()).append("\n\t")
                .append("Y coordinate : ").append(getCoordinates().getY()).append("\n\t")
                .append("Creation date : ").append(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(getCreationDate())).append("\n\t")
                .append("Price : ").append(getPrice()).append("\n\t")
                .append("Part number : ").append(getPartNumber()).append("\n\t")
                .append("Manufacture cost : ").append(getManufactureCost()).append("\n\t")
                .append("Unit of measurement : ").append(getUnitOfMeasure()).append("\n");
            if (getOwner() != null) {
                builder.append("\tOwner name : ").append(getOwner().getNameOwner()).append("\n")
                        .append("\tOwner birthday : ").append(DateTimeFormatter.ofPattern("dd.MM.yyyy").format(getOwner().getBirthday())).append("\n")
                        .append("\tOwner height : ").append(getOwner().getHeight()).append("\n")
                        .append("\tOwner weight : ").append(getOwner().getWeight()).append("\n")
                        .append("\tOwner passport id : ").append(getOwner().getPassportID()).append("\n");
            }else{
                builder.append("\tOwner: ").append(getOwner()).append("\n");
            }
            return builder.append("\tuser: ").append(username).append("\n").toString();
    }

    public int compareTo(Product o) {
        if (o == null) return 1;
        return this.getName().compareTo(o.getName());
    }

}