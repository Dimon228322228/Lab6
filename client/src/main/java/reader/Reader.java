package reader;

import content.BuilderProduct;
import content.Product;
import exceptions.InvalidProductFieldException;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.function.Consumer;

public class Reader {
    ExchangeController exchanger;
    public Reader(ExchangeController exchanger){
        this.exchanger = exchanger;
    }

    public String readCommand() throws IOException{
        return exchanger.readLine();
    }

    public Product readProduct() throws IOException {
        BuilderProduct builderProduct = new BuilderProduct();
        setField(builderProduct, builderProduct::setName).setField(builderProduct, builderProduct::setXCoordinate)
                .setField(builderProduct, builderProduct::setYCoordinate).setField(builderProduct, builderProduct::setPrice)
                .setField(builderProduct, builderProduct::setPartNumber).setField(builderProduct, builderProduct::setManufactureCost)
                .setField(builderProduct, builderProduct::setUnitOfMeasure);
        exchanger.writeMassage("Is there an owner?(Y/n)");
        String str = exchanger.readLine();
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
            setField(builderProduct, builderProduct::setPersonName).setField(builderProduct, builderProduct::setPersonBirthday)
                    .setField(builderProduct, builderProduct::setPersonHeight).setField(builderProduct, builderProduct::setPersonWeight)
                    .setField(builderProduct, builderProduct::setPersonPassportId);
        }
        return builderProduct.getProduct();
    }

    private Reader setField(BuilderProduct builderProduct, Consumer<String> setter) throws IOException{
        try{
            exchanger.writeMassage(builderProduct.getInvitation(setter));
            setter.accept(exchanger.readLine());
        } catch (InvalidProductFieldException | NumberFormatException | DateTimeException e){
            System.err.println(e.getMessage());
            System.out.println("Please, entered the field again: ");
            setField(builderProduct, setter);
        }
        return this;
    }
}
