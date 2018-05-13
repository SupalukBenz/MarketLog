package orm;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import program.PropertyManager;

import java.sql.SQLException;

/**
 * DatabaseManager is singleton class that create DAO classes for reused and not generated DAO classes
 * when internal ORMLite functionally.
 *
 * @author Supaluk Jaroensuk
 */
public class DatabaseManager {

    /**
     * Singleton class
     */
    private static DatabaseManager instance;

    /**
     * A reduction of the SQL DataSource.
     */
    private ConnectionSource connection;

    /**
     * DAO class of items.
     */
    private ItemsDao itemsDao = null;

    /**
     * DAO class of sales.
     */
    private SalesDao salesDao = null;

    /**
     * DAO class of order.
     */
    private OrderDao orderDao = null;

    /**
     * DAO class of sale details.
     */
    private SaleDetailDao saleDetailDao = null;

    /**
     * DAO class of reminders.
     */
    private RemindersDao remindersDao = null;

    /**
     * Initialize connection for implement its functionality outside of JDBC
     * and get property username, password and url of database.
     */
    public DatabaseManager(){
        String username = PropertyManager.getProperty("jdbc.user", "");
        String password = PropertyManager.getProperty("jdbc.password", "");
        String url = PropertyManager.getProperty("jdbc.url");

        try {
            connection = new JdbcConnectionSource(url, username, password);
        } catch (SQLException se){
            System.out.println("Cannot connect to database");
            System.out.println(se.getMessage());
        }
    }

    /**
     * Factory methods to be sure there is only one instance of a class.
     * @return singleton class
     */
    public static DatabaseManager getInstance(){
        if(instance == null) instance = new DatabaseManager();
        return instance;
    }

    /**
     * Initialize DAO class of items for handle all database operations.
     * @return ItemsDao class
     */
    public ItemsDao getItemDao(){
        if(itemsDao == null) {
            try {
                itemsDao = new ItemsDao(connection);
            } catch (SQLException se){
                System.out.println("Cannot connect to ItemDao.");
            }
        }
        return itemsDao;
    }

    /**
     * Initialize DAO class of sales for handle all database operations.
     * @return SalesDao class
     */
    public SalesDao getSalesDao(){
        if(salesDao == null) {
            try {
                salesDao = new SalesDao(connection);
            } catch (SQLException se){
                System.out.println("Cannot connect to SalesDao.");
            }
        }
        return salesDao;
    }

    /**
     * Initialize DAO class of order for handle all database operations.
     * @return OrderDao class
     */
    public OrderDao getOrderDao(){
        if(orderDao == null){
            try {
                orderDao = new OrderDao(connection);
            } catch (SQLException se){
                System.out.println("Cannot connect to OrderDao.");
            }
        }
        return orderDao;
    }

    /**
     * Initialize DAO class of sales details for handle all database operations.
     * @return SaleDetail class
     */
    public SaleDetailDao getSaleDetailDao(){
        if(saleDetailDao == null){
            try {
                saleDetailDao = new SaleDetailDao(connection);
            } catch (SQLException se){
                System.out.println("Cannot connect to SaleDetailDao.");
            }
        }
        return saleDetailDao;
    }

    /**
     * Initialize DAO class of reminders for handle all database operations.
     * @return RemindersDao class
     */
    public RemindersDao getRemindersDao(){
        if(remindersDao == null){
            try {
                remindersDao = new RemindersDao(connection);
            } catch (SQLException se){
                System.out.println("Cannot connect to SaleDetailDao.");
            }
        }
        return remindersDao;
    }
}
