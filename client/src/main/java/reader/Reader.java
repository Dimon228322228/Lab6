package reader;

import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;
import exceptions.UnknownCommandException;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.function.Consumer;

public class Reader {
    ExchangeController exchanger;
    public Reader(ExchangeController exchanger){
        this.exchanger = exchanger;
    }

    public String readCommand() throws IOException, UnknownCommandException{
        return exchanger.readLine();
//        List<String> parsedCommand = parseCommand(input);
//        if (parsedCommand.size() > 2) throw new UnknownCommandException();
//        if (commands.stream().map(CommandData::getName).anyMatch(parsedCommand.get(0)::equals)){
//                commandName = parsedCommand.get(0);
//        } else throw new UnknownCommandException();
//        if (checkedCommandByType(commandName, TypeCommand.ARG)){
//            try {
//                arg = parsedCommand.get(1);
//            } catch (IndexOutOfBoundsException e){
//                throw new UnknownCommandException("This command has a one argument");
//            }
//        }
//        else arg = "";
//        if (checkedCommandByType(commandName, TypeCommand.EXECUTED)) {
//            try{
//                File file = new File(arg);
//                requestions.addAll(new ReaderFromFile(file, commands).run());
//            } catch (NullPointerException ignored) {
//                System.err.println("No such this file.");
//                throw new UnknownCommandException("This command has a one argument");
//            }
//        }
//        if (checkedCommandByType(commandName, TypeCommand.PRODUCT)){
//            requestions.add(new Request(readProduct(), arg, commandName));
//        } else requestions.add(new Request(null, arg, commandName));
    }

    public Product readProduct() throws IOException {
        BuilderProduct builderProduct = new BuilderProduct();
        setField(builderProduct::setName).setField(builderProduct::setXCoordinate).setField(builderProduct::setYCoordinate)
                .setField(builderProduct::setPrice).setField(builderProduct::setPartNumber)
                .setField(builderProduct::setManufactureCost).setField(builderProduct::setUnitOfMeasure);
        exchanger.writeMassage("Is there an owner?(Y/n)");
        String str = exchanger.readLine();
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
            setField(builderProduct::setPersonName).setField(builderProduct::setPersonBirthday)
                    .setField(builderProduct::setPersonHeight).setField(builderProduct::setPersonWeight)
                    .setField(builderProduct::setPersonPassportId);
        }
        return builderProduct.getProduct();
    }

    private Reader setField(Consumer<String> setter) throws IOException{
        try{
            exchanger.writeMassage(BuilderProduct.getInvitation(setter));
            setter.accept(exchanger.readLine());
        } catch (InvalidProductFieldException | NumberFormatException | DateTimeException e){
            System.err.println(e.getMessage());
            System.out.println("Please, entered the field again: ");
            setField(setter);
        }
        return this;
    }
}
