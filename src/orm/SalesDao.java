package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Sales;

import java.sql.SQLException;
import java.util.List;

public class SalesDao extends BaseDaoImpl<Sales, Integer> {
    public SalesDao(ConnectionSource connectionSource) throws SQLException{
        super(connectionSource, Sales.class);
    }

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

}
