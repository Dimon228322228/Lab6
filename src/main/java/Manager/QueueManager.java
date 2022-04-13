package Manager;

import Content.Product;
import Content.UnitOfMeasure;
import Content.Validator.RealizedValidatorProduct;
import Exception.EmptyFileException;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import Exception.ProductNotFoundException;
/**
 * This class stores the collection and works with it
 * */
public class QueueManager implements CollectionManager{
    /**
     * creation date
     */
    private final ZonedDateTime date;
    /**
     * class that works with files
     */
    private final FileManager fileManager;
    /**
     * checks the correctness of the fields
     */
    private final RealizedValidatorProduct validatorProduct = new RealizedValidatorProduct();
    /**
     * path to the file where the collection is stored
     */
    private String filepath;
    /**
     * the collection
     */
    private PriorityQueue<Product> collection;
    /**
     * inner static class that generates the id
     */
    private static final GeneratedID generatedID = new GeneratedID();

    public QueueManager(FileManager fileManager){
        this.fileManager = fileManager;
        collection = new PriorityQueue<>();
        this.date = ZonedDateTime.now();
    }

    /**
     * Get id which don't exist in set
     * @return id
     */
    public static long getID() {
        return generatedID.getID();
    }


    /**
     * @return {@link List} with information about collection
     */
    @Override
    public List<String> displayInfo() {
        List<String> infoCollection = new ArrayList<>();
        infoCollection.add(collection.getClass().toString());
        infoCollection.add(String.valueOf(collection.size()));
        infoCollection.add(date.toString());
        return infoCollection;
    }

    /**
     * Checks objects received from a file and saves them to the collection
     */
    public void parseDateFromFile(){
        try{
            setFilepath();
            Collection<Product> products = fileManager.getCollectionFromFile(filepath);
            if (products.size() == 0) {
                System.out.println("Download failed");
                return;
            }
            for (Product product : products){
                if(validatorProduct.validProduct(product)){
                    collection.add(product);
                    generatedID.setID(product.getId());
                } else {
                    System.err.println("Product with " + product + "hasn't been add in the collection");
                }
            }
            System.out.println("Download complete");
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (EmptyFileException e) {
            System.err.println(new EmptyFileException().getMessage());
        }
    }

    /**
     * get value environment variable and set it to filepath
     */
    private void setFilepath(){
        System.out.println("Enter name environment variable that contains the path of the file: ");
        String nameVariable;
        System.getenv().forEach((k, v) ->
                System.out.println(k + " : " + v)
        );
        try {
            Scanner scr = new Scanner(System.in);
            nameVariable = scr.nextLine();
            filepath = System.getenv(nameVariable);
            if (filepath == null || filepath.equals("")) {
                System.out.println("This environment variable not exist.");
                setFilepath();
            }
        } catch (NoSuchElementException | IllegalStateException | SecurityException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * @return all elements of the collection as a list in ascending order
     */
    @Override
    public List<Product> showElements(){
        return getListProduct();
    }

    /**
     * @return all elements of the collection as a list in ascending order
     */
    private List<Product> getListProduct(){
        List<Product> productList = new ArrayList<>();
        PriorityQueue<Product> instance = new PriorityQueue<>();
        int size = collection.size();
        for (int i = 0; i < size; i++ ){
            Product product = collection.poll();
            productList.add(product);
            instance.add(product);
        }
        collection = instance;
        return productList;
    }

    /**
     * add product in the collection
     * @param product is haired of class {@link Product}
     */
    @Override
    public void add(Product product) {
        collection.add(product);
        generatedID.setID(product.getId());
    }

    /**
     * Deletes element of the collection by id
     * @param id elements id
     */
    @Override
    public void removeById(long id) {
        int size = collection.size();
        collection.stream()
                .sorted()
                .filter(x -> x.getId() == id)
                .forEach(collection::remove);
        if(size == collection.size())
            throw new ProductNotFoundException();
        else generatedID.removeID(id);
    }

    /**
     * Clear collection (Delete all element of the collection)
     */
    @Override
    public void clear() {
        collection = new PriorityQueue<>();
        generatedID.clearIdSet();
    }

    /**
     * Saves the collection to a file
     */
    @Override
    public void save() {
        if (filepath == null || filepath.equals("")) {
            setFilepath();
            try {
                fileManager.saveCollectionInXML(collection, filepath);
            } catch (JAXBException | IOException | InvalidPathException | EmptyFileException e){
                e.printStackTrace();
            }
        }else {
            try {
                fileManager.saveCollectionInXML(collection, filepath);
            } catch (JAXBException | IOException | InvalidPathException | EmptyFileException e){
                e.printStackTrace();            }
        }
    }

    /**
     * @return max product
     */
    private Product maxProduct() {
        Optional<Product> product = collection.stream().max(Product::compareTo);
        return product.orElse(null);
    }

    /**
     * Adds an element in the collection if it is larger than all the elements in the collection
     * @param product is hair of class {@link Product}
     */
    @Override
    public void addIfMax(Product product) {
        if (maxProduct() != null) {
            if (product.compareTo(maxProduct()) > 0) {
                add(product);
                System.out.println("Added success");
            } else {
                System.out.println("Added failed");
                generatedID.removeID(product.getId());
            }
        } else {
            add(product);
            System.out.println("Added success");
        }
    }

    /**
     * Deletes the smallest then given element from the collection
     * @param product is hair of class {@link Product}
     */
    @Override
    public void removeLower(Product product) {
        int size = collection.size();
        if (size != 0) {
            collection.stream()
                    .sorted()
                    .filter(x -> x.compareTo(product) < 0)
                    .forEach(x -> {
                        collection.remove(x);
                        generatedID.removeID(x.getId());
                    });
            System.out.println("Removing " + (size - collection.size()) + " elements");
        } else System.err.println("Nothing to remove! Collection is empty!");
    }

    /**
     * Counts the number of elements with the same manufacture cost
     * @return amount of elements
     */
    @Override
    public long countByManufactureCost(Double manufactureCost) {
        return collection.stream()
                .filter(x -> x.getManufactureCost() == manufactureCost)
                .count();
    }

    /**
     * Counts the number of elements with value unitOfMeasure grated than given
     * @param unitOfMeasure enum
     * @return amount of elements
     */
    @Override
    public long countGreaterThenUnitOfMeashure(UnitOfMeasure unitOfMeasure) {
        return collection.stream()
                .filter(x -> x.getUnitOfMeasure() != null)
                .filter(x -> x.getUnitOfMeasure().compareTo(unitOfMeasure) > 0)
                .count();
    }

    /**
     * method which leaves id product and remove unnecessary
     */
    public void autoUpdateId(){
        generatedID.autoUpdateId(collection);
    }
    /**
     * The class that generates an id
     */
    static class GeneratedID {
        /**
         * set existing id
         */
        private Set<Long> idSet = new HashSet<>();

        /**
         * Get id which don't exist in set
         * @return id
         */
        public long getID() {
            long id = 0;
            do{
                id ++;
            } while (idExists(id));
            idSet.add(id);
            return id;
        }

        /**
         * does an id exist in a set
         * @return true if exist
         */
        public boolean idExists(Long id){
            return idSet.contains(id);
        }

        /**
         * method which leaves id product and remove unnecessary
         */
        public void autoUpdateId(PriorityQueue<Product> collection){
            idSet = collection.stream()
                    .map(Product::getId)
                    .filter(idSet::contains)
                    .collect(Collectors.toSet());
        }

        /**
         * Remove an id from the set
         */
        public void removeID(long id) {
            idSet.remove(id);
        }

        public void setID(long id){idSet.add(id);}

        public void clearIdSet(){idSet = new HashSet<>();}
    }
}
