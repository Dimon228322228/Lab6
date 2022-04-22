package Content;

import Content.Caster.CasterPersonFromString;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * a class whose reply to owner product
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {

    @Setter @Getter private String name; // not null and not empty

    @XmlJavaTypeAdapter(value = LocalDateTimeSerializer.class)
    @Setter @Getter private LocalDateTime birthday; // not null

    @Setter @Getter private long height; // > 0

    @Setter @Getter private int weight; // > 0

    @Setter @Getter private String passportID; // not null, len(line) >= 6 and len(line) <= 41

    /**
     * creates fields person of different classes and checks their correctness
     */
    @XmlTransient
    private final CasterPersonFromString casterPersonFromString = new CasterPersonFromString();

    /**
     * set name owner from string, used in ConsoleReader
     */
    public void setNameStr(String inputStr){
        setName(casterPersonFromString.castName(inputStr));
    }

    /**
     * set birthday owner from string, used in ConsoleReader
     */
    public void setBirthdayStr(String inputStr){
        setBirthday(casterPersonFromString.castBirthday(inputStr));
    }

    /**
     * set height owner from string, used in ConsoleReader
     */
    public void setHeightStr(String inputStr){
        setHeight(casterPersonFromString.castHeight(inputStr));
    }

    /**
     * set weight owner from string, used in ConsoleReader
     */
    public void setWeightStr(String inputStr){
        setWeight(casterPersonFromString.castWeight(inputStr));
    }

    /**
     * set passport id from string, used in ConsoleReader
     */
    public void setPassportIDStr(String inputStr){
        setPassportID(casterPersonFromString.castPassportID(inputStr));
    }

    @Override
    public String toString(){
        return String.format("Person(owner) name = %s, birthday = %s," +
                " height = %d, weight = %s, passport ID = %s", name, birthday, height, weight, passportID);
    }
}
