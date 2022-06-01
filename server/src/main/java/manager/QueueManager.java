package manager;

import content.Product;
import content.UnitOfMeasure;
import exceptions.ProductNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * This class stores the collection and works with it
 * */
public class QueueManager implements CollectionManager{
    /**
     * creation date
     */
    private final ZonedDateTime date;

    /**
     * the collection
     */
    private final PriorityQueue<Product> collection;

    private static QueueManager instance;

    private QueueManager() {
        collection = new PriorityQueue<>();
        this.date = ZonedDateTime.now();
    }

    public static QueueManager getInstance() {
        if (instance == null) return new QueueManager();
        return instance;
    }

    /**
     * @return {@link List} with information about collection
     */
    @Override
    public List<String> displayInfo() {
        return new ArrayList<>(List.of(collection.getClass().toString(),
                                        String.valueOf(collection.size()),
                                        date.toString()));
    }

    /**
     * @return all elements of the collection as a list in ascending order
     */
    @Override
    public List<Product> showElements(){return collection.stream().sorted().toList();}

    /**
     * add product in the collection
     * @param product is haired of class {@link Product}
     */
    @Override
    public void add(Product product) {
        try {
            product.setCreationDate(new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").parse(new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date())));
        } catch (ParseException ignore) {}
        collection.add(product);
    }

    @Override
    public void addWithoutSetCreationDate(Product product){
        collection.add(product);
    }

    /**
     * Deletes element of the collection by id
     * @param id elements id
     */
    @Override
    public void removeById(long id, String username) {
        int size = collection.size();
        collection.stream()
                .sorted()
                .filter(x -> x.getId() == id)
                .filter(x -> x.getUsername().equals(username))
                .forEach(collection::remove);
        if(size == collection.size()) throw new ProductNotFoundException("This product belongs to another user or there is no such product with a given id. ");
    }

    /**
     * Clear collection (Delete all element of the collection)
     */
    @Override
    public void clear() {
        collection.clear();
    }

    @Override
    public void clearByUsername(String username) {
        collection.stream().sorted().filter(x -> x.getUsername().equals(username)).forEach(collection::remove);
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
    public boolean addIfMax(Product product) {
        if (product.compareTo(maxProduct()) > 0) {
            add(product);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void remove(Product product){
        collection.stream().sorted().filter(product::equals).forEach(collection::remove);
    }

    /**
     * Deletes the smallest then given element from the collection
     * @param product is hair of class {@link Product}
     */
    @Override
    public List<Product> removeLower(Product product, String username) {
        return new ArrayList<>(collection.stream()
                .sorted()
                .filter(x -> x.getUsername().equals(username))
                .filter(x -> x.compareTo(product) < 0)
                .peek(collection::remove)
                .toList());
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

    @Override
    public Product getById(long id){
        try{
            return collection.stream().filter(x -> x.getId() == id).toList().get(0);
        } catch (IndexOutOfBoundsException e){
            throw new ProductNotFoundException("There is no such product with a given id. ");
        }
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

}
