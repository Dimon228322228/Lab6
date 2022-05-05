package serverAction;

import serverAction.commandHandler.CommandHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class FileReader {
    protected BufferedReader reader;
    private boolean isRunning;
    protected CommandHandler commandFactory;

    public FileReader(CommandHandler commandFactory, File file) throws FileNotFoundException {
        reader = new BufferedReader(new java.io.FileReader(file));
        this.commandFactory = commandFactory;
    }

    public void readCommand() {
        isRunning = true;
        while (isRunning) {
            if (readyInput()) {
                try{
                    String input = reader.readLine();
                    String[] command = new String[0];
                    if (input == null){
                        System.err.println("Incorrectness finished executing");
                        System.exit(0);
                    }
                    else command = input.trim().split("[ ]+");
                    if (command.length == 1)
                        commandFactory.executeCommand(command[0], this, null);
                    else if (command.length == 2)
                        commandFactory.executeCommand(command[0], this, command[1]);
                    else System.err.println("No such this command!");
                } catch (IOException e){
                    e.printStackTrace();
                    exit();
                }
            } else isRunning = false;
        }
    }

    /**
     * @return true if file read stream ready
     */
    protected boolean readyInput() {
        boolean flag = false;
        try {
            flag = reader.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public void exit () {
        isRunning = false;
    }

    /**
     * don't demand repeat input field
     * @return entire product with filled out field
     * @throws IOException if IO exception occurred
     */
//    public Product readProduct() throws IOException {
//        Coordinates coordinates =  new Coordinates();
//        Product product = new Product();
//        product.setAutomaticGenerateField();
//        Person person = new Person();
//
//        try {
//            product.setNameStr(reader.readLine());
//
//            coordinates.setXStr(reader.readLine());
//            coordinates.setYStr(reader.readLine());
//
//            product.setCoordinates(coordinates);
//
//            product.setPriceStr(reader.readLine());
//            product.setPartNumberStr(reader.readLine());
//            product.setManufactureCostStr(reader.readLine());
//            product.setUnitOfMeasureStr(reader.readLine());
//
//            String str = reader.readLine();
//            if (str.equals("Y") || str.equals("y") || str.equals("Yes") || str.equals("yes")) {
//                person.setNameStr(reader.readLine());
//                person.setBirthdayStr(reader.readLine());
//                person.setHeightStr(reader.readLine());
//                person.setWeightStr(reader.readLine());
//                person.setPassportIDStr(reader.readLine());
//            } else {
//                person = null;
//            }
//            product.setOwner(person);
//        } catch (InvalidProductFieldException e){
//            System.err.println(e.getMessage());
//            product = null;
//        }
//        return product;
//    }
}
