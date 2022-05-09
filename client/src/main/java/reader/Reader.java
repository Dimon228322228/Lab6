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
