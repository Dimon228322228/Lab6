package Command.Reader;

import Command.CommandFactory.CommandFactory;
import Content.Coordinates;
import Content.Person;
import Content.Product;
import Manager.CollectionManager;
import Exception.InvalidProductFieldException;

import java.io.*;


public class FileReader extends AbstractReader {
    public FileReader(CommandFactory commandFactory, CollectionManager manager, File file) throws FileNotFoundException {
        reader = new BufferedReader(new java.io.FileReader(file));
        super.commandFactory = commandFactory;
        super.manager = manager;
    }

    /**
     * @return true if file read stream ready
     */
    @Override
    protected boolean readyInput() {
        boolean flag = false;
        try {
            flag = reader.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * don't demand repeat input field
     * @return entire product with filled out field
     * @throws IOException if IO exception occurred
     */
    @Override
    public Product readProduct() throws IOException {
        Coordinates coordinates =  new Coordinates();
        Product product = new Product();
        product.setAutomaticGenerateField();
        Person person = new Person();

        try {
            product.setNameStr(reader.readLine());

            coordinates.setXStr(reader.readLine());
            coordinates.setYStr(reader.readLine());

            product.setCoordinates(coordinates);

            product.setPriceStr(reader.readLine());
            product.setPartNumberStr(reader.readLine());
            product.setManufactureCostStr(reader.readLine());
            product.setUnitOfMeasureStr(reader.readLine());

            String str = reader.readLine();
            if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
                person.setNameStr(reader.readLine());
                person.setBirthdayStr(reader.readLine());
                person.setHeightStr(reader.readLine());
                person.setWeightStr(reader.readLine());
                person.setPassportIDStr(reader.readLine());
            } else {
                person = null;
            }
            product.setOwner(person);
        } catch (InvalidProductFieldException e){
            System.err.println(e.getMessage());
            product = null;
        }
        return product;
    }
}
