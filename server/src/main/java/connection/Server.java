package connection;

import action.ResultAction;
import exceptions.InvalidRecievedException;
import serverAction.CommandHandler;
import transmission.Request;
import transmission.Response;
import transmissionServer.HandlerMessageServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    volatile boolean runningFlag;

    private final int port;
    private final CommandHandler commandHandler;
    private final BufferedReader bufferedReader;
    private final Selector selector;
    private ServerSocketChannel serChannel;
    private SocketChannel readableChannel;
    HandlerMessageServer handlerMessage = new HandlerMessageServer();
    private final Map<SocketChannel, Request> registrationRequest = new ConcurrentHashMap<>();


    public Server(int port, CommandHandler commandHandler) throws IOException {
        this.commandHandler = commandHandler;
        this.port = port;
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        selector = initSelector();
        commandHandler.getCollectionFromFile();
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
        handlerMessage.sendCommandData(socketChannel, commandHandler);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void readByKey(SelectionKey key) {
        readableChannel = (SocketChannel) key.channel();
        Request request = null;
        try {
            request = handlerMessage.readMessage(readableChannel);
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

    private void writeByKey(SelectionKey key) {
        SocketChannel writeableChannel = (SocketChannel) key.channel();
        Request clientRequest = registrationRequest.get(writeableChannel);
        commandHandler.setRequest(clientRequest);
        ResultAction answer = commandHandler.executeCommand(clientRequest.getCommandName());
        Response response = new Response(answer);
        try {
            handlerMessage.sendMessage(writeableChannel, response);
        } catch (IOException | ClassCastException e) {
            System.err.println(e.getMessage());
            closeChannel(writeableChannel);
        }
        try{
            writeableChannel.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            System.err.println(e.getMessage());
        }

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
