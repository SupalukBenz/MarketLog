package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Item;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO class of items which implements Base class for the Database Access Objects
 * that handle the reading and writing a class from the database.
 *
 * @author Supaluk Jaroensuk
 */
public class ItemsDao extends BaseDaoImpl<Item, Integer> {

    /**
     * Initialize DAO class for handle all database operations.
     * @param connectionSource A reduction of the SQL DataSource.
     * @throws SQLException an exception that provides information on a database access error or other errors.
     */
    public ItemsDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Item.class);
    }

    /**
     * Search data in database and store in list.
     * @param tableColumn is column name in table.
     * @param search is key for search.
     * @return list of items that user searched.
     */
    public List<Item> searchByColumnName(String tableColumn, String search){
        QueryBuilder<Item, Integer> qb = this.queryBuilder();
        List<Item> itemList = null;

        try {
            itemList = qb.where().eq(tableColumn, search).query();
        } catch (SQLException se){
            System.out.println("Cannot search by items data.");
        }

        return itemList;
    }

    /**
     * Counting total data in table of database.
     * @param dao is Iterable of items.
     * @return total data, as a int.
     */
    public int getLastIdItem(Iterable<Item> dao){
        int lastId = 0;
        for(Item item: dao){
            lastId++;
        }
        return lastId;
    }

    /**
     * Search data in database and then store in list.
     * @param tableColumn is column name in table.
     * @param key is key for search.
     * @return Item of list.
     */
    public Item getItemFromKey(String tableColumn, String key){
        List<Item> getItem = searchByColumnName(tableColumn, key);
        return getItem.get(0);
    }

}
