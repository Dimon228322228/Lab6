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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandHandler {
    private final ValidatorCommand validatorCommand;
    private final ExchangeController exchangeController;
    private final Reader reader;
    private final CommandController controller;
    private final HandlerMesClient handlerMesClient;
    private final Session session;

    public CommandHandler(CommandController commandController, HandlerMesClient handlerMesClient, Session session){
        exchangeController = new ExchangeController();
        reader = new Reader(exchangeController);
        controller = commandController;
        validatorCommand = new ValidatorCommand(controller.getFullCommandData());
        this.handlerMesClient = handlerMesClient;
        this.session = session;
    }

    public void run()  {
        Optional<Product> product = Optional.empty();
        Optional<String> commandOptional = readCommand();
        String arg = "";
        if (commandOptional.isEmpty()) return;
        String commandline = commandOptional.get();
        List<String> commandList = parseCommand(commandline);
        if(validatorCommand.commandValidString(commandList) && validatorCommand.isCommandPresent(commandList.get(0))){
            if (validatorCommand.argPresent(commandList.get(0), commandList)) {
                arg = commandList.get(1);
            } else {
                exchangeController.writeErr("You didn't write any argument of this command");
                run();
            }
            if (validatorCommand.productPresent(commandList.get(0))){
                product = readProduct();
                if (product.isEmpty()) return;
            }
            Optional<ResultAction> resultActionOptional;
            if (validatorCommand.isUserCommand(commandList.get(0))) resultActionOptional = handleUserCommand(commandList.get(0), arg);
            else resultActionOptional = handleServerCommand(commandList.get(0), arg, product.orElse(null));
            if (resultActionOptional.isPresent()){
                try{
                    boolean result = handleResultAction(resultActionOptional.get());
                    if(result) run();
                } catch (IOException e) {
                    exchangeController.writeErr("The IOException has been occurred. ");
                }
            }
            try {
                session.disconnect();
            } catch (IOException ignore) {}
        } else {
            exchangeController.writeErr("This command is not found. Checked number arguments or name command. ");
            run();
        }
    }

    private List<String> parseCommand(String input){
        return Arrays.asList(input.trim().split("[ ]+ "));
    }

    private Optional<String> readCommand(){
        try {
            return Optional.of(reader.readCommand());
        } catch (IOException e) {
            exchangeController.writeErr("The IOException has been occurred. ");
            return Optional.empty();
        }
    }

    private Optional<Product> readProduct(){
        try {
            return Optional.of(reader.readProduct());
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
        controller.addCommandInHistory(command);
        Request request = new Request(product, arg, command);
        try {
            handlerMesClient.sendMessage(session.getSocketChannel(), request);
            Response response = handlerMesClient.readMessage(session.getSocketChannel());
            return Optional.of(response.getResultAction());
        } catch (IOException | InvalidRecievedException e) {
            exchangeController.writeErr("Sorry, exception is occurred. " + e.getMessage());
            return Optional.empty();
        }
    }

    private boolean handleResultAction(ResultAction resultAction) throws IOException {
        String answer = resultAction.getDescription();
        switch (resultAction.getState()){
            case ERROR -> exchangeController.writeErr("Error: " + answer);
            case SUCCESS -> exchangeController.writeMassage(answer);
            case FAILED -> exchangeController.writeErr(answer);
        }
        return resultAction.getState() != State.EXIT;
    }
}
