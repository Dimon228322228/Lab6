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

    /**
     * Remove element with this id
     */
    void removeById(long id);

    /**
     * Remove all element in collection
     */
    void clear();

    /**
     * Upload collection in file
     */
    void save();

    /**
     * Add element in collection if he is great then all
     */
    boolean addIfMax(Product product);

    /**
     * Remove all element lower then given
     */
    int removeLower(Product product);

    /**
     * Count all element then manufactureCost equals given
     */
    long countByManufactureCost(Double manufactureCost);

    /**
     * Count amount element then greater given
     */
    long countGreaterThenUnitOfMeashure(UnitOfMeasure unitOfMeasure);

    void remove(Product product);
}
