package orm;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import program.PropertyManager;

import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;
    private ConnectionSource connection;
    private ItemsDao itemsDao = null;
    private SalesDao salesDao = null;
    private OrderDao orderDao = null;
    private SaleDetailDao saleDetailDao = null;
    private RemindersDao remindersDao = null;

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

    public static DatabaseManager getInstance(){
        if(instance == null) instance = new DatabaseManager();
        return instance;
    }

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
