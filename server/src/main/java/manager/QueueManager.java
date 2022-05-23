package manager;

import content.Product;
import content.UnitOfMeasure;
import exceptions.ProductNotFoundException;

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
     * set creation date and set unique id
     */
    public void setAutomaticGenerateField(Product product){
        product.setCreationDate(new Date());
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
        setAutomaticGenerateField(product);
        collection.add(product);
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
        if(size == collection.size()) throw new ProductNotFoundException();
    }

    /**
     * Clear collection (Delete all element of the collection)
     */
    @Override
    public void clear() {
        collection.clear();
    }

    /**
     * Saves the collection to a file
     */
    @Override
    public void save() {

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
    public int removeLower(Product product) {
        int size = collection.size();
        if (size != 0) {
            collection.stream()
                    .sorted()
                    .filter(x -> x.compareTo(product) < 0)
                    .forEach(collection::remove);
            return size - collection.size();
        } else return 0;
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

}
