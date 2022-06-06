package manager;

import content.Product;
import content.UnitOfMeasure;
import exceptions.ProductNotFoundException;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import manager.database.DatabaseManager;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class stores the collection and works with it
 * */
@Log4j2
public class QueueManager implements CollectionManager{
    /**
     * creation date
     */
    private final ZonedDateTime date;

    /**
     * the collection
     */
    private final PriorityQueue<Product> collection;

    @Setter private DatabaseManager databaseManager;
    private static QueueManager instance;

    private QueueManager() {
        collection = new PriorityQueue<>();
        this.date = ZonedDateTime.now();
    }

    public synchronized static QueueManager getInstance() {
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
     * @return state of the adding product in the collection
     */
    @Override
    public synchronized boolean add(Product product) throws SQLException {
        if (product == null) return false;
        try {
            product.setCreationDate(new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").parse(new SimpleDateFormat("dd.MM.yyyy HH.mm.ss").format(new Date())));
        } catch (ParseException ignore) {}
        product.setId(databaseManager.executeInsert(product));
        collection.add(product);
        return true;
    }

    @Override
    public synchronized void addWithoutSetCreationDate(Product product){
        collection.add(product);
    }

    /**
     * Deletes element of the collection by id
     * @param id elements id
     */
    @Override
    public synchronized boolean removeById(long id, String username) {
        AtomicReference<Product> product = new AtomicReference<>();
        if (collection.stream().filter(x -> x.getId() == id).filter(x -> x.getUsername().equals(username))
                .peek(product::set).count() == 0)
            throw new ProductNotFoundException("This product belongs to another user or there is no such product with a given id. ");
        try {
            databaseManager.executeDeletedById(id);
        } catch (SQLException e) {
            collection.add(product.get());
            return false;
        }
        return true;
    }

    @Override
    public synchronized boolean updateById(long id, Product product, String username) {
        if (collection.stream().filter(x -> x.getId() == id).filter(x -> x.getUsername().equals(username))
                .peek(x -> {
                    product.setCreationDate(x.getCreationDate());
                    product.setId(id);
                }).count() == 0)
            throw new ProductNotFoundException("This product belongs to another user or there is no such product with a given id. ");
        try {
            databaseManager.executeUpdateById(product, (int) id);
        } catch (SQLException e) {
            return false;
        }
        collection.stream().sorted().filter(x -> x.getId() == id).filter(x -> x.getUsername().equals(username))
                .forEach(collection::remove);
        collection.add(product);
        return true;
    }

    @Override
    public synchronized void clearByUsername(String username) throws SQLException {
        databaseManager.executeClearCollection(username);
        collection.stream().sorted().filter(x -> x.getUsername().equals(username)).forEach(collection::remove);
    }

    /**
     * @return max product
     */
    private synchronized Product maxProduct() {
        Optional<Product> product = collection.stream().max(Product::compareTo);
        return product.orElse(null);
    }

    /**
     * Adds an element in the collection if it is larger than all the elements in the collection
     * @param product is hair of class {@link Product}
     */
    @Override
    public synchronized boolean addIfMax(Product product) throws SQLException {
        if (product.compareTo(maxProduct()) > 0) {
            return add(product);
        } else {
            return false;
        }
    }

    /**
     * Deletes the smallest then given element from the collection
     * @param product is hair of class {@link Product}
     */
    @Override
    public synchronized int removeLower(Product product, String username) {
        List<Product> products = new ArrayList<>(collection.stream()
                .sorted()
                .filter(x -> x.getUsername().equals(username))
                .filter(x -> x.compareTo(product) < 0)
                .peek(collection::remove)
                .toList());
        int count = products.size();
        for (Product product1: products){
            try {
                databaseManager.executeDeletedById(product1.getId());
            } catch (SQLException e) {
                collection.add(product1);
                count--;
            }
        }
        return count;
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
