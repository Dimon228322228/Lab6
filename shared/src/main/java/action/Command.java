package action;

public interface Command {
    ResultAction execute();
    CommandData getCommandData();
}
