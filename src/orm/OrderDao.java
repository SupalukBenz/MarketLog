package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
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

}
