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
        setField("name", builderProduct::setName).setField("x", builderProduct::setXCoordinate)
                .setField("y", builderProduct::setYCoordinate).setField("price", builderProduct::setPrice)
                .setField("part number", builderProduct::setPartNumber).setField("cost", builderProduct::setManufactureCost)
                .setField("unit", builderProduct::setUnitOfMeasure);
        exchanger.writeMassage("Is there an owner?(Y/n)");
        String str = exchanger.readLine();
        if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
            setField("oName", builderProduct::setPersonName).setField("oBirthday", builderProduct::setPersonBirthday)
                    .setField("height", builderProduct::setPersonHeight).setField("weight", builderProduct::setPersonWeight)
                    .setField("passport", builderProduct::setPersonPassportId);
        }
        return builderProduct.getProduct();
    }

    private Reader setField(String descr, Consumer<String> setter) throws IOException{
        try{
            exchanger.writeMassage(BuilderProduct.getInvitation(descr));
            setter.accept(exchanger.readLine());
        } catch (InvalidProductFieldException | NumberFormatException | DateTimeException e){
            System.err.println(e.getMessage());
            System.out.println("Please, entered the field again: ");
            setField(descr, setter);
        }
        return this;
    }
}
