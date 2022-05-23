package transmission;

import authentication.Account;
import content.Product;
import lombok.Getter;

import java.io.Serializable;

public class Request implements Serializable {
    @Getter private final Product product;
    @Getter private final String arg;
    @Getter private final String commandName;
    @Getter private final Target target;
    @Getter private final Account account;

    public Request(Product product, String arg, String commandName, Target target, Account account) {
        this.product = product;
        this.arg = arg;
        this.commandName = commandName;
        this.target = target;
        this.account = account;
    }
}
