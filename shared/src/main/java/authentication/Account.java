package authentication;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Account implements Serializable {
    @Getter private final String password;
    @Getter private final String name;
    @Getter @Setter private TypeAuthentication type;

    public Account(String name, String password, TypeAuthentication type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "username='" + name + '\'' +
                "}";
    }
}
