package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Item;
import program.SaleDetail;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO class of sales details which implements Base class for the Database Access Objects
 * that handle the reading and writing a class from the database.
 *
 * @author Supaluk Jaroensuk
 */
public class SaleDetailDao extends BaseDaoImpl<SaleDetail, Integer> {

    /**
     * Initialize DAO class for handle all database operations.
     * @param connectionSource A reduction of the SQL DataSource.
     * @throws SQLException an exception that provides information on a database access error or other errors.
     */
    public SaleDetailDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SaleDetail.class);
    }

    /**
     * Search data in database and store in list.
     * @param tableColumn is column name in table.
     * @param search is key for search.
     * @return list of sales details that user searched.
     */
    public List<SaleDetail> searchByColumnName(String tableColumn, int search){
        QueryBuilder<SaleDetail, Integer> qb = this. queryBuilder();
        List<SaleDetail> saleList = null;

        try {
            saleList = qb.where().eq(tableColumn, search).query();
        } catch (SQLException se){
            System.out.println("Cannot search in sales data.");
        }

        return saleList;
    }

    /**
     * Decrease quantity of item stock.
     * @param itemsDao is ItemDao class.
     * @param itemName is item name that was bought.
     * @param orderQty is quantity of order.
     */
    public void updateQuantityItem(ItemsDao itemsDao, String itemName, int orderQty){
        Item item = itemsDao.getItemFromKey("name_item", itemName);
        int result = item.getQuantity_item() - orderQty;
        Item update = new Item(item.getId_item(), item.getName_item(), item.getDescription_item(), item.getTotal_item(), result);

        try {
            itemsDao.update(update);
        } catch (SQLException e) {
            System.out.println("Cannot update quantity of item from sale.");
            e.printStackTrace();
        }
    }


}
