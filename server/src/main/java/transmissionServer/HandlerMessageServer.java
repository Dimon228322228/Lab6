package transmissionServer;

import exceptions.InvalidRecievedException;
import serverAction.CommandHandler;
import transmission.HandlerMessage;
import transmission.Request;
import transmission.Response;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class HandlerMessageServer extends HandlerMessage {
    public void sendCommandData(SocketChannel channel, CommandHandler commandHandler) throws IOException {
        sendMessage(channel, commandHandler.getCommandDataForUser());
    }

    public <T> void sendResponse(SocketChannel channel, T response) throws IOException {
        sendMessage(channel, response);
    }

    public Request getRequest(SocketChannel channel) throws IOException, InvalidRecievedException {
        try {
            return (Request) Objects.requireNonNull(getMessage(channel)).readObject();
        } catch (ClassNotFoundException | ClassCastException | NullPointerException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
    }

}
