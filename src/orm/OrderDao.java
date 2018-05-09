package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Item;
import program.Order;
import program.Sales;

import java.sql.SQLException;
import java.util.List;

public class OrderDao extends BaseDaoImpl<Order, String> {

    public OrderDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Order.class);
    }

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

    public Order getOrderFromKey(String orderName){
        List<Order> orderList = searchByColumnName("item_order", orderName);
        return orderList.get(0);
    }

    public void editedOrder(OrderDao orders,String itemName, int orderAddQty, double orderTotal){
        Order order = orders.getOrderFromKey(itemName);
        System.out.println("current order = " + order.getQuantity());
        int updateQty = order.getQuantity() + orderAddQty;
        System.out.println("add order = " + updateQty);
        double updateTotal = order.getTotal() + orderTotal;
        Order newOrder = new Order(order.getItem(), order.getDescription(), updateQty, updateTotal);

        try {
            orders.update(newOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getAmountOfOrder(OrderDao orders){
        double amount = 0;
        for(Order order: orders){
            amount += order.getTotal();
        }
        return amount;
    }


}
