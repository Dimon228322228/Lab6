package manager;

import content.Product;
import content.UnitOfMeasure;
import java.util.List;

public interface CollectionManager {
    /**
     * display info about collection
     */
    List<String> displayInfo();

    /**
     *  Show all element of collection in string
     */
    List<Product> showElements();

    /**
     * Add new element in collection
     */
    void add(Product product);

    void addWithoutSetCreationDate(Product product);

    /**
     * Remove element with this id
     */
    void removeById(long id, String username);

    /**
     * Remove all element in collection
     */
    void clear();

    /**
     * Remove all element by username
     */
    void clearByUsername(String username);
    /**
     * Add element in collection if he is great then all
     */
    boolean addIfMax(Product product);

    /**
     * Remove all element lower then given
     */
    List<Product> removeLower(Product product, String username);

    /**
     * Count all element then manufactureCost equals given
     */
    long countByManufactureCost(Double manufactureCost);

    Product getById(long id);

    /**
     * Count amount element then greater given
     */
    long countGreaterThenUnitOfMeashure(UnitOfMeasure unitOfMeasure);

    void remove(Product product);
}
