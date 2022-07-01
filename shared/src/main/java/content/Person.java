package content;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * a class whose reply to owner product
 */
public class Person implements Serializable {

    @Setter @Getter private String nameOwner; // not null and not empty

    @Setter @Getter private LocalDateTime birthday; // not null

    @Setter @Getter private long height; // > 0

    @Setter @Getter private int weight; // > 0

    @Setter @Getter private String passportID; // not null, len(line) >= 6 and len(line) <= 41

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person person)) return false;
        if (this == obj) return true;
        return Objects.equals(nameOwner, person.getNameOwner()) &&
                Objects.equals(birthday, person.getBirthday()) &&
                Objects.equals(height, person.getHeight()) &&
                Objects.equals(weight, person.getWeight()) &&
                Objects.equals(passportID, person.getPassportID());
    }

    @Override
    public String toString(){
        return String.format("Person(owner) name = %s, birthday = %s," +
                " height = %d, weight = %s, passport ID = %s", nameOwner, birthday, height, weight, passportID);
    }
}
