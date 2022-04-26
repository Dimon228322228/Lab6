package Action;

import java.util.Set;

public interface Command {
    Set<TypeCommand> getType();
    String getName();
}
