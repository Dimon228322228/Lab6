package Reader;

import Content.BuilderProduct;
import Content.Product;
import Exceptions.InvalidProductFieldException;
import Messager.EnglishMessenger;
import Messager.Messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.util.function.Consumer;

public class ReaderConsole implements Reader{
    BufferedReader reader = new BufferedReader(System.console().reader());
    @Override
    public void readCommand() {

    }

    @Override
    public void exit() {

    }

    @Override
    public Product readProduct() throws IOException {
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
}
