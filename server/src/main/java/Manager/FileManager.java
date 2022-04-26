package Manager;

import Content.Coordinates;
import Content.Person;
import Content.Product;
import Exceptions.EmptyFileException;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.PriorityQueue;

/**
 * Class that handles files for parsing
 */
public class FileManager {
    private final Marshaller jaxbMarshaller;
    private final Unmarshaller jaxbUnmarshaller;

    public FileManager() throws JAXBException {
        JAXBContext xmlContext = JAXBContext.newInstance(Product.class, Coordinates.class, Person.class, CollectionQueuer.class);
        jaxbMarshaller = xmlContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        jaxbUnmarshaller = xmlContext.createUnmarshaller();
    }

    /**
     * Checks that the file exists, is not empty and can be read and written
     * @return object {@link File} with given file path
     * @throws InvalidPathException if file path invalid
     * @throws IOException if I/O exception occurred
     * @throws EmptyFileException if file is empty
     */
    public File assertFileIsUsable(String dataFilePath) throws InvalidPathException, IOException, EmptyFileException {
        String filePath = Paths.get(dataFilePath).toAbsolutePath().toString();
        File fileToRetrieve = new File(filePath);
        if (!fileToRetrieve.exists())
            throw new FileNotFoundException("There is not such file!");
        else if (fileToRetrieve.length() == 0)
            throw new EmptyFileException();
        if (!fileToRetrieve.canRead() || !fileToRetrieve.canWrite())
            throw new SecurityException();
        return fileToRetrieve;
    }

    /**
     * Saves the collection in XML format in file with indicated file name
     * @throws IOException if I/O exception occurred
     * @throws InvalidPathException if file path invalid
     * @throws JAXBException If there was an error in the parser
     * @throws EmptyFileException if file is empty
     */
    public void saveCollectionInXML(PriorityQueue<Product> collection, String fileName) throws IOException, InvalidPathException, JAXBException, EmptyFileException {
        try (BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(assertFileIsUsable(fileName)))) {
            CollectionQueuer queueproduct = new CollectionQueuer();
            queueproduct.setProducts(collection);
            jaxbMarshaller.marshal(queueproduct, os);
        }
        System.out.println("Collection has been save successful");
    }

    /**
     * Using getStrFromFile
     * @return unchecked a collection with product from file
     * @throws JAXBException If there was an error in the parser
     * @throws EmptyFileException if file is empty
     */
    public PriorityQueue<Product> getCollectionFromFile(String filepath) throws JAXBException, EmptyFileException {
        PriorityQueue<Product> collection = new PriorityQueue<>();
        String dataStr = null;
        try {
            dataStr = this.getStrFromFile(filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (dataStr != null && !dataStr.equals("")) {
            try(StringReader reader = new StringReader(dataStr)) {
                collection = ((CollectionQueuer) jaxbUnmarshaller.unmarshal(reader)).getProducts();
            }
        }
        return collection;
    }

    /**
     * use assertFileIsUsable to check correctness file
     * @return string of all symbols from file
     * @throws IOException if I/O exception occurred
     * @throws InvalidPathException if file path invalid
     * @throws EmptyFileException if file is empty
     */
    public String getStrFromFile(String filePath) throws IOException, InvalidPathException, EmptyFileException {
        File fileToRetrieve = assertFileIsUsable(filePath);
        int len = 0;
        try {
            len = (int) fileToRetrieve.length();
        } catch (ClassCastException ex) {
            System.err.println("Error cast len file in integer");
        }
        String dataStr;
        char[] cbuf = new char[len];
        try (FileReader fileReader = new FileReader(fileToRetrieve)) {
            //noinspection ResultOfMethodCallIgnored
            fileReader.read(cbuf);
            dataStr = new String(cbuf);
        }
        return dataStr;
    }

    /**
     * a class for wrapping elements of the collection
     */
    @XmlRootElement(name = "Products")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class CollectionQueuer implements Serializable {
        @Setter @Getter private PriorityQueue<Product> products = new PriorityQueue<>();
    }

}