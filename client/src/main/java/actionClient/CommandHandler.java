package actionClient;

import action.ResultAction;
import action.State;
import connection.Session;
import content.Product;
import exceptions.InvalidRecievedException;
import reader.ExchangeController;
import reader.Reader;
import transmission.Request;
import transmission.Response;
import transmissionClient.HandlerMesClient;
import util.Supplier;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandHandler {
    private final ExchangeController exchangeController;
    private final Reader reader;
    private final CommandController controller;
    private final HandlerMesClient handlerMesClient;
    private final Session session;
    private boolean flag = true;

    public CommandHandler(HandlerMesClient handlerMesClient, Session session){
        exchangeController = new ExchangeController();
        reader = new Reader(exchangeController);
        controller = new CommandController(this, exchangeController);
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
            product = read(reader::readProduct);
            if (product.isEmpty()) {
                exchangeController.writeErr("For executing command need a product. ");
                return;
            }
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

    private List<String> parseCommand(String input){
        return Arrays.asList(input.trim().split("[ ]+ "));
    }

    private <T> Optional<T> read(Supplier<T> supplier){
        try {
            return Optional.of(supplier.get());
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
        Request request = new Request(product, arg, command);
        try {
            handlerMesClient.sendMessage(session.getSocketChannel(), request);
            Response response = handlerMesClient.readMessage(session.getSocketChannel());
            controller.addCommandInHistory(command);
            return Optional.of(response.getResultAction());
        } catch (IOException | InvalidRecievedException e) {
            exchangeController.writeErr("Sorry, exception is occurred. " + e.getMessage());
            return Optional.empty();
        }
    }

    private boolean handleResultAction(ResultAction resultAction) {
        String answer = resultAction.getDescription();
        switch (resultAction.getState()){
            case ERROR -> exchangeController.writeErr("Error: " + answer);
            case SUCCESS -> {
                try {
                    exchangeController.writeMassage(answer);
                } catch (IOException e) {
                    exchangeController.writeErr(e.getMessage());
                }
            }
            case FAILED -> exchangeController.writeErr(answer);
        }
        return resultAction.getState() != State.EXIT;
    }
}