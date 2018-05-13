package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO class of order which implements Base class for the Database Access Objects
 * that handle the reading and writing a class from the database.
 *
 * @author Supaluk Jaroensuk
 */
public class OrderDao extends BaseDaoImpl<Order, String> {

    /**
     * Initialize DAO class for handle all database operations.
     * @param connectionSource A reduction of the SQL DataSource.
     * @throws SQLException an exception that provides information on a database access error or other errors.
     */
    public OrderDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Order.class);
    }

    /**
     * Search data in database and store in list.
     * @param tableColumn is column name in table.
     * @param search is key for search.
     * @return list of items that user searched.
     */
    public List<Order> searchByColumnName(String tableColumn, String search){
        QueryBuilder<Order, String> qb = this. queryBuilder();
        List<Order> orderList = null;

        try {
            orderList = qb.where().eq(tableColumn, search).query();
        } catch (SQLException se){
            System.out.println("Cannot search in sales data.");
        }

        return orderList;
    }

    /**
     * Search data in database and then store in list.
     * @param orderName is key for search.
     * @return Order of list.
     */
    public Order getOrderFromKey(String orderName){
        List<Order> orderList = searchByColumnName("item_order", orderName);
        return orderList.get(0);
    }

    /**
     * Update quantity and total of order when adding order by same item.
     * @param orders is OrderDao class.
     * @param itemName is item name that want to edit.
     * @param orderAddQty is quantity and total that was added.
     * @param orderTotal
     */
    public void editedOrder(OrderDao orders,String itemName, int orderAddQty, double orderTotal){
        Order order = orders.getOrderFromKey(itemName);
        int updateQty = order.getQuantity() + orderAddQty;
        double updateTotal = order.getTotal() + orderTotal;
        Order newOrder = new Order(order.getItem(), order.getDescription(), updateQty, updateTotal);
        try {
            orders.update(newOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get amount all of orders.
     * @param orders is OrderDao class.
     * @return amount all of orders.
     */
    public double getAmountOfOrder(OrderDao orders){
        double amount = 0;
        for(Order order: orders){
            amount += order.getTotal();
        }
        return amount;
    }


}
