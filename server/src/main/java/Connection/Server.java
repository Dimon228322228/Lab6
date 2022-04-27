package Connection;

import Action.CommandManager;
import Exceptions.InvalidRecievedException;
import Manager.CollectionManager;
import Transmission.Message;
import Transmission.Request;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    volatile boolean runningFlag;

    private final int port;
    private final CollectionManager collectionManager;
    private CommandManager commandManager;
    private final BufferedReader bufferedReader;
    private final Selector selector;
    private ServerSocketChannel serChannel;
    private SocketChannel readableChannel;
    HandleMessage handleMessage = new HandleMessage();
    private final Map<SocketChannel, Request> registrationRequest = new ConcurrentHashMap<>();


    public Server(int port, CollectionManager collectionManager, CommandManager commandManager) throws IOException {
        this.collectionManager = collectionManager;
        this.commandManager = commandManager;
        this.port = port;
        bufferedReader = new BufferedReader(System.console().reader());
        selector = initSelector();
    }

    private Selector initSelector() throws IOException{
        Selector selector = Selector.open();
        serChannel = ServerSocketChannel.open();
        serChannel.configureBlocking(false);
        serChannel.socket().bind(new InetSocketAddress(port));
        serChannel.register(selector, SelectionKey.OP_ACCEPT);
        return selector;
    }

    public void run(){
        runningFlag = true;
        while (runningFlag){
            try{
                selector.select(200);
            } catch (IOException ignored){}
            handleClient();
            handleServerInput();
        }
    }

    private void handleClient(){
        Set<SelectionKey> keys = selector.selectedKeys();
        for (Iterator<SelectionKey> iterator = keys.iterator(); iterator.hasNext();){
            SelectionKey key = iterator.next();
            iterator.remove();
            handleSelectionKey(key);
        }
    }

    private void handleSelectionKey(SelectionKey key){
        if (!key.isValid()) return;
        try{
            if (key.isAcceptable()){
                createNewChannel();
            } else if (key.isReadable()){
                readByKey(key);
            } else if (key.isWritable()){
                writeByKey(key);
            }
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    private void createNewChannel() throws IOException {
        SocketChannel socketChannel = serChannel.accept();
        socketChannel.configureBlocking(false);
        sendCommandManager(socketChannel);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void readByKey(SelectionKey key) {
        readableChannel = (SocketChannel) key.channel();
        Request request = null;
        try {
            Message message = handleMessage.readMessage(readableChannel);
            request = (Request) message;
        } catch (IOException | InvalidRecievedException | ClassCastException e) {
            System.err.println(e.getMessage());
            closeChannel(readableChannel);
        }
        registerRequest(request);
    }



    private void closeChannel(SocketChannel socketChannel){
        try{
            socketChannel.close();
        } catch (IOException ignore){}
    }

    private void registerRequest(Request request){
        registrationRequest.put(readableChannel, request);
        try{
            readableChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            System.err.println(e.getMessage());
        }
    }

    private void writeByKey(SelectionKey key){
        //write on channel and register it as readable
    }

    private void sendCommandManager(SocketChannel channel) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(commandManager);
        ByteBuffer byteBuffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        channel.write(byteBuffer);
    }

    private void handleServerInput(){
        String inputStr;
        try{
            inputStr = bufferedReader.readLine();
        } catch (IOException ignore){
            return;
        }
        if (null == inputStr || inputStr.trim().equals("exit")){
            exit();
        }
    }

    private void exit(){
        runningFlag = false;
        closing();
    }

    private void closing(){
        try{
            selector.close();
            serChannel.socket().close();
            serChannel.close();
        } catch (IOException e){
            System.err.println("Can't closing socket");
        }
    }

}
