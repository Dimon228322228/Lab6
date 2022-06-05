package transmissionServer;

import lombok.extern.log4j.Log4j2;
import transmission.Response;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;

@Log4j2
public class ResponseSender extends RecursiveAction {
    private final Future<Response> responseFuture;
    private final HandlerMessageServer handlerMessage;
    private final SocketChannel channel;

    public ResponseSender(Future<Response> responseFuture, HandlerMessageServer handlerMessage, SocketChannel channel){
        this.responseFuture = responseFuture;
        this.handlerMessage = handlerMessage;
        this.channel = channel;
    }

    @Override
    protected void compute() {
        try {
            Response response = responseFuture.get();
            handlerMessage.sendResponse(channel, response);
            log.info(response.getResultAction().getState() + " - state response which has sent. \n");
        } catch (InterruptedException | ExecutionException | IOException e) {
            log.info(e.getMessage());
        }
    }
}
