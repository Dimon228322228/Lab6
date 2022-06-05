package connection;

import exceptions.InvalidRecievedException;
import lombok.extern.log4j.Log4j2;
import serverAction.CommandHandler;
import transmission.Request;
import transmission.Response;
import transmissionServer.HandlerMessageServer;
import transmissionServer.RequestHandler;
import transmissionServer.ResponseSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Log4j2
public class Server {
    volatile boolean runningFlag;

    private final int port;
    private final CommandHandler commandHandler;
    private final BufferedReader bufferedReader;
    private final Selector selector;
    private ServerSocketChannel serChannel;
    HandlerMessageServer handlerMessage = new HandlerMessageServer();
    private final Map<SocketChannel, Future<Response>> registerResponseFuture = new ConcurrentHashMap<>();
    private final ForkJoinPool forkJoinPool = new ForkJoinPool(4);
    private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


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
        SocketChannel readableChannel = (SocketChannel) key.channel();
        Request request;
        try {
            request = handlerMessage.getRequest(readableChannel);
            if (request == null) return;
            log.info("Given new request: " + request.getCommandName() + ". ");
            Future<Response> responseFuture = cachedThreadPool.submit(new RequestHandler(commandHandler, request));
            registerResponseFuture.put(readableChannel, responseFuture);
            readableChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (ClosedChannelException e) {
            log.error(e.getMessage());
        } catch (IOException | InvalidRecievedException | ClassCastException e) {
            log.error(e.getMessage());
            log.info("Channel has been disconnect. ");
            try{
                readableChannel.close();
            } catch (IOException ignore){}
        }
    }

    private void writeByKey(SelectionKey key) {
        SocketChannel writeableChannel = (SocketChannel) key.channel();
        forkJoinPool.submit(new ResponseSender(registerResponseFuture.get(writeableChannel), handlerMessage, writeableChannel));
        try{
            writeableChannel.register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            log.error(e.getMessage());
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
