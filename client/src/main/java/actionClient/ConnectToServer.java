package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import connection.Session;
import exceptions.InvalidRecievedException;

import java.io.IOException;
import java.util.Set;

public class ConnectToServer extends AbstractCommandClient{
    Session session;
    CommandHandler commandHandler;
    public ConnectToServer(Session session, CommandHandler commandHandler){
        super("connect",
                Set.of(TypeCommand.USER),
                "connect to server. ");
        this.session = session;
        this.commandHandler = commandHandler;
    }

    @Override
    public ResultAction execute() {
        if(session.reconnect(1)){
            try {
                commandHandler.updateCommandData();
            } catch (InvalidRecievedException | IOException e) {
                return new ResultAction(State.FAILED, "Failed connect to server. ");
            }
            return new ResultAction(State.SUCCESS, "Connect to server. ");
        }
        else return new ResultAction(State.FAILED, "Failed connect to server. ");
    }
}
