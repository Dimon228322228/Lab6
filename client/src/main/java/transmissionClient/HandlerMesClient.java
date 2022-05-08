package transmissionClient;

import action.CommandData;
import exceptions.InvalidRecievedException;
import transmission.Request;
import transmission.Response;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

public class HandlerMesClient {
    public List<CommandData> getCommandData(SocketChannel channel) throws IOException, InvalidRecievedException {
        byte[] buffer = new byte[8192];
        List<CommandData> recievedMessage;
        int bytesRead = channel.read(ByteBuffer.wrap(buffer));
        if (bytesRead == 0) {
            return null;
        }
        ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
        try {
            recievedMessage = (List<CommandData>) objectStream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
        return recievedMessage;
    }

    public Response readMessage(SocketChannel channel) throws IOException, InvalidRecievedException {
        byte[] buffer = new byte[8192];
        Response recievedMessage;
        int bytesRead = channel.read(ByteBuffer.wrap(buffer));
        if (bytesRead == 0) {
            return null;
        }
        ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
        try {
            recievedMessage = (Response) objectStream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
        return recievedMessage;
    }

    public void sendMessage(SocketChannel channel, Request request) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);

        ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayOutputStream);
        objectStream.writeObject(request);

        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        channel.write(byteBuffer);
    }
}
