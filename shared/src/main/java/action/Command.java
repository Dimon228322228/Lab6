package action;

public interface Command {
    void execute();
    CommandData getCommandData();
}
