package reader;

import action.CommandData;
import action.TypeCommand;
import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;
import exceptions.UnknownCommandException;
import messager.EnglishMessenger;
import messager.Messenger;
import transmission.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ReaderConsole implements Reader{
    BufferedReader reader = new BufferedReader(System.console().reader());
    List<CommandData> commands;
    public ReaderConsole(List<CommandData> commands){
        this.commands = commands;
    }

    private List<Request> readCommand() throws IOException, UnknownCommandException{
        String input = reader.readLine();
        List<String> parsedCommand = parseCommand(input);
        List<Request> requestions = new ArrayList<>();
        String commandName;
        String arg;
        if (parsedCommand.size() > 2) throw new UnknownCommandException();
        if (commands.stream().map(CommandData::getName).anyMatch(parsedCommand.get(0)::equals)){
                commandName = parsedCommand.get(0);
        } else throw new UnknownCommandException();
        if (checkedCommandByType(commandName, TypeCommand.ARG)){
            try {
                arg = parsedCommand.get(1);
            } catch (IndexOutOfBoundsException e){
                throw new UnknownCommandException("This command has a one argument");
            }
        }
        else arg = "";
        if (checkedCommandByType(commandName, TypeCommand.EXECUTED)) {
            try{
                File file = new File(arg);
                requestions.addAll(new ReaderFromFile(file, commands).run());
            } catch (NullPointerException ignored) {
                System.err.println("No such this file.");
                throw new UnknownCommandException("This command has a one argument");
            }
        }
        if (checkedCommandByType(commandName, TypeCommand.PRODUCT)){
            requestions.add(new Request(readProduct(), arg, commandName));
        } else requestions.add(new Request(null, arg, commandName));
        return requestions;
    }

    private Product readProduct() throws IOException {
        BuilderProduct builderProduct = new BuilderProduct();
        Messenger messenger = EnglishMessenger.getInstance();

        System.out.println(messenger.getFieldInvitationMessage("name"));
        setField(reader.readLine(), builderProduct::setName);

        System.out.println(messenger.getFieldInvitationMessage("x"));
        setField(reader.readLine(), builderProduct::setXCoordinate);

        System.out.println(messenger.getFieldInvitationMessage("y"));
        setField(reader.readLine(), builderProduct::setYCoordinate);

        System.out.println(messenger.getFieldInvitationMessage("price"));
        setField(reader.readLine(), builderProduct::setPrice);

        System.out.println(messenger.getFieldInvitationMessage("partNumber"));
        setField(reader.readLine(), builderProduct::setPartNumber);

        System.out.println(messenger.getFieldInvitationMessage("manufactureCost"));
        setField(reader.readLine(), builderProduct::setManufactureCost);

        System.out.println(messenger.getUnitOfMeasureInputInvitationMessage());
        setField(reader.readLine(), builderProduct::setUnitOfMeasure);

        System.out.println("Is there an owner?(Y/n)");
        String str = reader.readLine();
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
            System.out.println(messenger.getFieldInvitationMessage("namePerson"));
            setField(reader.readLine(), builderProduct::setPersonName);

            System.out.println(messenger.getPersonBirthdayInputInvitationMessage());
            setField(reader.readLine(), builderProduct::setPersonBirthday);

            System.out.println(messenger.getFieldInvitationMessage("height"));
            setField(reader.readLine(), builderProduct::setPersonHeight);

            System.out.println(messenger.getFieldInvitationMessage("weight"));
            setField(reader.readLine(), builderProduct::setPersonWeight);

            System.out.println(messenger.getFieldInvitationMessage("passportId"));
            setField(reader.readLine(), builderProduct::setPersonPassportId);
        }
        return builderProduct.getProduct();
    }

    private void setField(String input, Consumer<String> setter) throws IOException{
        try{
            setter.accept(input);
        } catch (InvalidProductFieldException | NumberFormatException | DateTimeException e){
            System.err.println(e.getMessage());
            System.out.println("Please, entered the field again: ");
            setField(reader.readLine(), setter);
        }
    }

    private List<String> parseCommand(String input){
        return Arrays.stream(input.trim().split("[ ]+ ")).toList();
    }

    private boolean checkedCommandByType(String commandName, TypeCommand type){
        return commands.stream()
                .filter(x -> x.getName().equals(commandName))
                .map(CommandData::getTypes)
                .anyMatch(x -> x.contains(type));
    }

    @Override
    public List<Request> run() throws UnknownCommandException, IOException{
        return readCommand();
    }
}
