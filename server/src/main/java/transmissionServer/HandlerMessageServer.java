package transmissionServer;

import exceptions.InvalidRecievedException;
import serverAction.CommandHandler;
import transmission.Request;
import transmission.Response;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HandlerMessageServer {
    public void sendCommandData(SocketChannel channel, CommandHandler commandHandler) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandHandler.getCommandDataForUser());
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        channel.write(byteBuffer);
    }
    public Request readMessage(SocketChannel channel) throws IOException, InvalidRecievedException {
        byte[] buffer = new byte[8192];
        Request recievedMessage;
        int bytesRead = channel.read(ByteBuffer.wrap(buffer));
        if (bytesRead == 0) {
            return null;
        }
        ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
        try {
            recievedMessage = (Request) objectStream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
        return recievedMessage;
    }

    public void sendMessage(SocketChannel channel, Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);

        ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayOutputStream);
        objectStream.writeObject(response);

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        channel.write(byteBuffer);
    }

}
