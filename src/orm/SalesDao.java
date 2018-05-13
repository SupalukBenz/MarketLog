package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Sales;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO class of sales which implements Base class for the Database Access Objects
 * that handle the reading and writing a class from the database.
 *
 * @author Supaluk Jaroensuk
 */
public class SalesDao extends BaseDaoImpl<Sales, Integer> {

    /**
     * Initialize DAO class for handle all database operations.
     * @param connectionSource A reduction of the SQL DataSource.
     * @throws SQLException an exception that provides information on a database access error or other errors.
     */
    public SalesDao(ConnectionSource connectionSource) throws SQLException{
        super(connectionSource, Sales.class);
    }

    /**
     * Search data in database and store in list.
     * @param tableColumn is column name in table.
     * @param search is key for search.
     * @return list of sales that user searched.
     */
    public List<Sales> searchByColumnName(String tableColumn, String search){
        QueryBuilder<Sales, Integer> qb = this. queryBuilder();
        List<Sales> saleList = null;

        try {
            saleList = qb.where().eq(tableColumn, search).query();
        } catch (SQLException se){
            System.out.println("Cannot search in sales data.");
        }

        return saleList;
    }

    /**
     * Search data in database by receipt id and store in list.
     * @param receiptId is receipt id that use to searching.
     * @return list of sales.
     */
    public List<Sales> searchByReceiptId(int receiptId){
        QueryBuilder<Sales, Integer> qb = this. queryBuilder();
        List<Sales> saleList = null;

        try {
            saleList = qb.where().eq("receipt_id", receiptId).query();
        } catch (SQLException se){
            System.out.println("Cannot search in sales data.");
        }

        return saleList;
    }

    /**
     * Get sale details from receipt id.
     * @param receiptId is receipt id that use to search.
     * @return sales details.
     */
    public Sales getUpdateUnpaidFromId(int receiptId){
        List<Sales> saleList = searchByReceiptId(receiptId);
        return saleList.get(0);
    }


}
