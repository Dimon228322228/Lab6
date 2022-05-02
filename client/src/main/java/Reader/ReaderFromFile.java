package Reader;

import Content.BuilderProduct;
import Content.Product;
import Exceptions.InvalidProductFieldException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReaderFromFile implements Reader{
    BufferedReader reader;

    public ReaderFromFile(File file) throws FileNotFoundException {
        reader = new BufferedReader(new java.io.FileReader(file));
    }

    @Override
    public void readCommand() {

    }

    @Override
    public void exit() {

    }

    @Override
    public Product readProduct() throws IOException, InvalidProductFieldException {
        BuilderProduct builderProduct = new BuilderProduct();
        builderProduct.setNameChain(reader.readLine())
                .setXCoordinateChain(reader.readLine())
                .setYCoordinateChain(reader.readLine())
                .setPriceChain(reader.readLine())
                .setPartNumberChain(reader.readLine())
                .setManufactureCostChain(reader.readLine())
                .setUnitOfMeasure(reader.readLine());
        String str = reader.readLine();
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
            builderProduct.setPersonNameChain(reader.readLine())
                    .setPersonBirthdayChain(reader.readLine())
                    .setPersonHeightChain(reader.readLine())
                    .setPersonWeightChain(reader.readLine())
                    .setPersonPassportId(reader.readLine());
        }
        return builderProduct.getProduct();
    }
}
