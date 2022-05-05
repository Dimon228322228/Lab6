package action;

import lombok.Getter;

import java.util.Set;

public class CommandData {
    @Getter String name;
    @Getter Set<TypeCommand> types;
    @Getter String description;
    public CommandData(String name, Set<TypeCommand> types, String description){
        this.name = name;
        this.types = types;
        this.description = description;
    }
}
