package transmissionServer;

import serverAction.CommandHandler;
import transmission.HandlerMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HandlerMessageServer extends HandlerMessage {
    public void sendCommandData(SocketChannel channel, CommandHandler commandHandler) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandHandler.getCommandDataForUser());
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        channel.write(byteBuffer);
    }

}
