package connection;

import action.ResultAction;
import action.State;
import exceptions.InvalidRecievedException;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
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
                // CompletableFuture.supplyAsync(handleCommand, cachedThreadPool).andThen(writeByKey, forkJoinPool)
                readByKey(key);
            } else if (key.isWritable()){
                writeByKey(key);
            }
        } catch (IOException e){
            log.error(e.getMessage());
        }
    }

    private void createNewChannel() throws IOException {
        SocketChannel socketChannel = serChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        log.info("New channel is connected. ");
    }

    private void readByKey(SelectionKey key) {
        readableChannel = (SocketChannel) key.channel();
        Request request = null;
        try {
            request = handlerMessage.getRequest(readableChannel);
        } catch (IOException | InvalidRecievedException | ClassCastException e) {
            log.info("Channel has been disconnect. ");
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
        if (request != null) registrationRequest.put(readableChannel, request);
        else return;
        log.info("Given new request: " + request.getCommandName() + ". ");
        try{
            readableChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            log.error(e.getMessage());
        }
    }

    private void writeByKey(SelectionKey key) {
        SocketChannel writeableChannel = (SocketChannel) key.channel();
        Request clientRequest = registrationRequest.get(writeableChannel);
        handleClientRequest(writeableChannel, clientRequest);
        try{
            writeableChannel.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            log.error(e.getMessage());
        }
    }

    private void handleClientRequest(SocketChannel channel, Request request){
        try {
            switch (request.getTarget()) {
                case EXECUTECOMMAND -> {
                    commandHandler.setRequest(request);
                    ResultAction answer = commandHandler.executeCommand(request.getCommandName());
                    Response response = new Response(answer);
                    handlerMessage.sendResponse(channel, response);
                    log.info(response.getResultAction().getState() + " - state response which has sent. \n");
                }
                case GETCOMMANDDATA -> {
                    handlerMessage.sendCommandData(channel, commandHandler);
                    log.info("Command data has been sent. \n");
                }
                case AUTHENTICATION -> {
                    if (commandHandler.checkAuthentication(request))
                        handlerMessage.sendResponse(channel, new Response(new ResultAction(State.SUCCESS, commandHandler.getAuthenticationMessage())));
                    else handlerMessage.sendResponse(channel, new Response(new ResultAction(State.FAILED, commandHandler.getAuthenticationMessage())));
                }
            }
        } catch (IOException | ClassCastException e) {
                log.error(e.getMessage());
                closeChannel(channel);
        }
    }

    private void handleServerInput(){
        String inputStr;
        try{
            if (System.in.available() == 0) return;
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
            log.error("Can't closing socket");
        }
    }
}
