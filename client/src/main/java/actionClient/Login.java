package actionClient;

import action.ResultAction;
import action.State;
import action.TypeCommand;
import authentication.CurrentAccount;
import authentication.ReaderLoginData;
import connection.Session;
import exceptions.InvalidRecievedException;
import transmissionClient.HandlerMesClient;

import java.io.IOException;
import java.util.Set;

public class Login extends AbstractCommandClient{
    Session clientSession;
    HandlerMesClient handlerMessage;
    CommandHandler commandHandler;
    public Login(Session session, HandlerMesClient handlerMessage, CommandHandler commandHandler){
        super("login",
                Set.of(TypeCommand.USER),
                "login in server. ");
        clientSession = session;
        this.handlerMessage = handlerMessage;
        this.commandHandler = commandHandler;
    }

    @Override
    public ResultAction execute() {
        CurrentAccount.setAccount(ReaderLoginData.logIn());
        if(!waitConnection(clientSession)) {
            return new ResultAction(State.FAILED, "Failed connect to the server. Try again later. ");
        } else {
            String answerAuthen;
            try{
                handlerMessage.sendAuthentication(clientSession.getSocketChannel());
                answerAuthen = handlerMessage.getAuthenticationResponse(clientSession.getSocketChannel());
            } catch (IOException | InvalidRecievedException e) {
                disconnect(clientSession);
                return new ResultAction(State.FAILED, "Can't send or get authentication data. " + e.getMessage() +
                        " Please, try authentication again with command 'login'. ");
            }
            try {
                commandHandler.updateCommandData();
            } catch (IOException | InvalidRecievedException | NullPointerException e) {
                disconnect(clientSession);
                return new ResultAction(State.FAILED, "Can't get the list of commands from server.");
            }
            return new ResultAction(State.SUCCESS, "Connected to the server. " + answerAuthen);
        }
    }

    private boolean waitConnection(Session clientSession){
        return clientSession.reconnect(3);
    }

    private void disconnect(Session session){
        try {
            session.disconnect();
        } catch (IOException ignored) {}
    }
}
