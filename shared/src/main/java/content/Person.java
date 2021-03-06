package content;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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

    @Override
    public String toString(){
        return String.format("Person(owner) name = %s, birthday = %s," +
                " height = %d, weight = %s, passport ID = %s", name, birthday, height, weight, passportID);
    }
}
