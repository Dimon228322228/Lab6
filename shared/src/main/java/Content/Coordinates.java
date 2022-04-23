package Content;

import Content.Caster.CasterCoordinatesFromString;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * a class whose store x and y coordinate product
 */
public class Coordinates implements Serializable {

    @Getter @Setter private Integer x; // field not null and <= 938

    @Getter @Setter private Integer y; // field not null

    /**
     * creates fields coordinates and checks their correctness
     */
    @XmlTransient
    private final CasterCoordinatesFromString casterCoordinatesFromString = new CasterCoordinatesFromString();

    /**
     * set x coordinate from string, used in ConsoleReader
     */
    public void setXStr(String inputStr){
        setX(casterCoordinatesFromString.castX(inputStr));
    }

    /**
     * set y coordinate from string, used in ConsoleReader
     */
    public void setYStr(String inputStr){
        setY(casterCoordinatesFromString.castY(inputStr));
    }

    @Override
    public String toString(){
        return String.format("Coordinate X and Y = (%d,%d) ", x, y);
    }
}
