package Connection;

import Exceptions.InvalidRecievedException;
import Transmission.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HandleMessage {
    public Message readMessage(SocketChannel channel) throws IOException, InvalidRecievedException {
        byte[] buffer = new byte[8192];
        Message recievedMessage;
        int bytesRead = channel.read(ByteBuffer.wrap(buffer));
        if (bytesRead == 0) {
            return null;
        }
        ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
        try {
            recievedMessage = (Message) objectStream.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            throw new InvalidRecievedException("Failed cast input string to message");
        }
        return recievedMessage;
    }

}
