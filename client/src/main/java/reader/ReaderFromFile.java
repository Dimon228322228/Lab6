package reader;

import action.CommandData;
import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;
import exceptions.UnknownCommandException;
import transmission.Request;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ReaderFromFile {
    BufferedReader reader;
    List<CommandData> commandData;

    public ReaderFromFile(File file, List<CommandData> commandData) throws FileNotFoundException {
        reader = new BufferedReader(new java.io.FileReader(file));
        this.commandData = commandData;
    }

    private List<Request> readCommand() {
        List<String> strings = reader.lines().toList();
        return null;
    }

    public List<Request> run() throws UnknownCommandException, IOException {
        return  null;
    }

    public Product readProduct() throws IOException, InvalidProductFieldException {
        BuilderProduct builderProduct = new BuilderProduct();
        builderProduct.setName(reader.readLine())
                .setXCoordinate(reader.readLine())
                .setYCoordinate(reader.readLine())
                .setPrice(reader.readLine())
                .setPartNumber(reader.readLine())
                .setManufactureCost(reader.readLine())
                .setUnitOfMeasure(reader.readLine());
        String str = reader.readLine();
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
            builderProduct.setPersonName(reader.readLine())
                    .setPersonBirthday(reader.readLine())
                    .setPersonHeight(reader.readLine())
                    .setPersonWeight(reader.readLine())
                    .setPersonPassportId(reader.readLine());
        }
        return builderProduct.getProduct();
    }

    private boolean isCommandInclude(String input){
        return commandData.stream().map(CommandData::getName).anyMatch(x -> x.equals(parseCommand(input).get(0)));
    }

    private List<String> parseCommand(String input){
        return Arrays.stream(input.trim().split("[ ]+ ")).toList();
    }
}
