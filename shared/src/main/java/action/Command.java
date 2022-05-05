package action;

public interface Command {
    String execute();
    CommandData getCommandData();
}
