package content;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * a class whose store x and y coordinate product
 */
public class Coordinates implements Serializable {

    @Getter @Setter private Integer x; // field not null and <= 938

    @Getter @Setter private Integer y; // field not null

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Coordinates coordinates)) return false;
        if (this == obj) return true;
        return Objects.equals(coordinates.getX(), x) && Objects.equals(coordinates.getY(), y);
    }

    @Override
    public String toString(){
        return String.format("Coordinate X and Y = (%d,%d) ", x, y);
    }
}
