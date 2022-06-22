package actionClient;

import action.ResultAction;
import action.State;
import authentication.CurrentAccount;
import connection.Session;
import content.Product;
import exceptions.InvalidRecievedException;
import lombok.Getter;
import lombok.Setter;
import reader.ExchangeController;
import reader.Reader;
import transmission.Request;
import transmission.Response;
import transmission.Target;
import transmissionClient.HandlerMesClient;
import utilites.Supplier;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CommandHandler {
    private final ExchangeController exchangeController;
    private final Reader reader;
    private final CommandController controller;
    private final HandlerMesClient handlerMesClient;
    private final Session session;
    private boolean flag = true;
    @Getter @Setter private boolean executionMode = false;

    public CommandHandler(HandlerMesClient handlerMesClient, Session session){
        exchangeController = new ExchangeController();
        reader = new Reader(exchangeController);
        controller = new CommandController(this, exchangeController, session, handlerMesClient);
        this.handlerMesClient = handlerMesClient;
        this.session = session;
    }

    public void run(){
        while (flag){
            handleCommand();
        }
    }

    public void resetFlag(){
        flag = false;
    }
    public void setFlag(){
        flag = true;
    }

    public void handleCommand()  {
        Optional<Product> product = Optional.empty();
        ValidatorCommand validatorCommand = new ValidatorCommand(controller.getFullCommandData());
        Optional<String> commandOptional = read(reader::readCommand);
        String arg = "";
        if (commandOptional.isEmpty()) return;
        String commandline = commandOptional.get();
        List<String> commandList = parseCommand(commandline);
        if(!(validatorCommand.commandValidString(commandList) && validatorCommand.isCommandPresent(commandList.get(0)))){
            exchangeController.writeErr("This command is not found. Checked number arguments or name command. ");
            return;
        }
        if (validatorCommand.argPresent(commandList.get(0))) {
            if (commandList.size() != 2) {
                exchangeController.writeErr("You didn't write any argument of this command");
                return;
            }
            arg = commandList.get(1);
        }
        if (validatorCommand.productPresent(commandList.get(0))){
            if (isExecutionMode()) exchangeController.replaceOut(new BufferedWriter(new StringWriter()));
            product = read(reader::readProduct);
            if (product.isEmpty()) {
                exchangeController.writeErr( commandList.get(0) + ": For executing command need a product. ");
                return;
            }
            exchangeController.replaceOut(new BufferedWriter(new OutputStreamWriter(System.out)));
        }
        Optional<ResultAction> resultActionOptional;
        if (validatorCommand.isUserCommand(commandList.get(0))) resultActionOptional = handleUserCommand(commandList.get(0), arg);
        else resultActionOptional = handleServerCommand(commandList.get(0), arg, product.orElse(null));
        if (resultActionOptional.isEmpty()) return;
        boolean result = handleResultAction(resultActionOptional.get());
        if(!result) resetFlag();
    }

    public void updateCommandData() throws InvalidRecievedException, IOException {
        controller.setServerCommandsData(handlerMesClient.getCommandData(session.getSocketChannel()));
    }

    public void resetCommandData(){
        controller.setServerCommandsData(null);
    }

    public ResultAction login(){
        return controller.executeCommand("login");
    }

    private List<String> parseCommand(String input){return Arrays.stream(input.trim().split("[ ]+")).collect(Collectors.toList());}

    private <T> Optional<T> read(Supplier<T> supplier){
        try {
            return Optional.of(supplier.get());
        } catch (NullPointerException | EOFException e) {
            resetFlag();
            return Optional.empty();
        } catch (IOException e) {
            exchangeController.writeErr("The IOException has been occurred. ");
            return Optional.empty();
        }
    }

    private Optional<ResultAction> handleUserCommand(String command, String arg) {
        controller.addCommandInHistory(command);
        controller.setArgCommand(arg);
        return Optional.of(controller.executeCommand(command));
    }

    private Optional<ResultAction> handleServerCommand(String command, String arg, Product product) {
        Request request = new Request(product, arg, command, Target.EXECUTECOMMAND, CurrentAccount.getAccount());
        try {
            handlerMesClient.sendRequest(session.getSocketChannel(), request);
            Response response = handlerMesClient.getResponse(session.getSocketChannel());
            controller.addCommandInHistory(command);
            return Optional.of(response.getResultAction());
        } catch (IOException e) {
            exchangeController.writeErr("Server hasn't responded. Try again late. ");
            if (!session.reconnect(1)) {
                return Optional.empty();
            }
            try {
                controller.setServerCommandsData(handlerMesClient.getCommandData(session.getSocketChannel()));
            } catch (IOException | InvalidRecievedException | NullPointerException ignored) {
            }
            return Optional.empty();
        } catch (InvalidRecievedException e){
            exchangeController.writeErr("Sorry, exception is occurred. " + e.getMessage());
            return Optional.empty();
        }
    }

    private boolean handleResultAction(ResultAction resultAction) {
        String answer = resultAction.getDescription();
        switch (resultAction.getState()){
            case ERROR, FAILED -> exchangeController.writeErr(answer);
            case SUCCESS -> {
                try {
                    exchangeController.writeMassage(answer);
                } catch (IOException e) {
                    exchangeController.writeErr(e.getMessage());
                }
            }
        }
        return resultAction.getState() != State.EXIT;
    }
}
