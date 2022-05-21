package transmissionClient;

import action.CommandData;
import exceptions.InvalidRecievedException;
import transmission.HandlerMessage;
import transmission.Request;
import transmission.Response;
import transmission.Target;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Objects;

public class HandlerMesClient extends HandlerMessage {
    public void sendRequest(SocketChannel channel, Request request) throws IOException {
        sendMessage(channel, request);
    }

    @SuppressWarnings("unchecked")
    public List<CommandData> getCommandData(SocketChannel channel) throws IOException, InvalidRecievedException {
        sendRequest(channel, new Request(null, null, null, Target.GETCOMMANDDATA));
        try {
            return (List<CommandData>) Objects.requireNonNull(getMessage(channel)).readObject();
        } catch (ClassNotFoundException | ClassCastException | NullPointerException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
    }

    public Response getResponse(SocketChannel channel) throws IOException, InvalidRecievedException {
        try {
            return (Response) Objects.requireNonNull(getMessage(channel)).readObject();
        } catch (ClassNotFoundException | ClassCastException | NullPointerException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
    }

}
