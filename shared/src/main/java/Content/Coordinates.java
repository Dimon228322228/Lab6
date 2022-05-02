package Content;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * a class whose store x and y coordinate product
 */
public class Coordinates implements Serializable {

    @Getter @Setter private Integer x; // field not null and <= 938

    @Getter @Setter private Integer y; // field not null

    @Override
    public String toString(){
        return String.format("Coordinate X and Y = (%d,%d) ", x, y);
    }
}
