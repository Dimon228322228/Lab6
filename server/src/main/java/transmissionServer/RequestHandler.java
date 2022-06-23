package transmissionServer;

import action.ResultAction;
import action.State;
import lombok.extern.log4j.Log4j2;
import serverAction.CommandHandler;
import transmission.Request;
import transmission.Response;

import java.util.concurrent.Callable;

@Log4j2
public class RequestHandler implements Callable<Response> {

    private final CommandHandler commandHandler;
    private final Request request;


    public RequestHandler(CommandHandler commandHandler, Request request){
        this.commandHandler = commandHandler;
        this.request = request;
    }

    @Override
    public Response call(){
        switch (request.getTarget()) {
            case EXECUTECOMMAND -> {
                ResultAction answer = commandHandler.executeCommand(request.getCommandName(), request);
                return new Response(answer);
            }
            case GETCOMMANDDATA -> {
                Response response = new Response(new ResultAction(State.SUCCESS, ""));
                response.setCommandData(commandHandler.getCommandDataForUser());
                return response;
            }
            case AUTHENTICATION -> {
                if (commandHandler.checkAuthentication(request))
                    return new Response(new ResultAction(State.SUCCESS, commandHandler.getAuthenticationMessage()));
                else return new Response(new ResultAction(State.FAILED, commandHandler.getAuthenticationMessage()));
            }
            case GETCOLLECTION -> {
                ResultAction resultAction = commandHandler.executeCommand("getCollection", request);
                return new Response(resultAction);
            }
        }
        log.info(Thread.currentThread().getName() + "finished. ");
        return new Response(new ResultAction(State.FAILED, ""));
    }
}
