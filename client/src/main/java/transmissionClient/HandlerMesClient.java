package transmissionClient;

import action.CommandData;
import exceptions.InvalidRecievedException;
import transmission.HandlerMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;

public class HandlerMesClient extends HandlerMessage {
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
        return recievedMessage;    }
}
