package transmission;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class HandlerMessage {
    protected <T> void sendMessage(SocketChannel channel, T t) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
        ObjectOutputStream objectStream = new ObjectOutputStream(byteArrayOutputStream);
        objectStream.writeObject(t);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        channel.write(byteBuffer);
    }

    protected ObjectInputStream getMessage(SocketChannel channel) throws IOException{
        byte[] buffer = new byte[8192];
        int bytesRead = channel.read(ByteBuffer.wrap(buffer));
        if (bytesRead == 0) return null;
        return new ObjectInputStream(new ByteArrayInputStream(buffer));
    }
}
