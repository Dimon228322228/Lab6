package manager;

import content.Product;
import content.UnitOfMeasure;

import java.sql.SQLException;
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
     * @return
     */
    boolean add(Product product) throws SQLException;

    void addWithoutSetCreationDate(Product product);

    /**
     * Remove element with this id
     */
    boolean removeById(long id, String username);

    boolean updateById(long id, Product product, String username);

    /**
     * Remove all element by username
     */
    void clearByUsername(String username) throws SQLException;
    /**
     * Add element in collection if he is great then all
     */
    boolean addIfMax(Product product) throws SQLException;

    /**
     * Remove all element lower then given
     */
    List<Product> removeLower(Product product, String username);

    /**
     * Count all element then manufactureCost equals given
     */
    List<Product> countByManufactureCost(Double manufactureCost);

    Product getById(long id);

    /**
     * Count amount element then greater given
     */
    List<Product> countGreaterThenUnitOfMeashure(UnitOfMeasure unitOfMeasure);
}
