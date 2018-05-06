package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Item;
import program.SaleDetail;

import java.sql.SQLException;
import java.util.List;

public class SaleDetailDao extends BaseDaoImpl<SaleDetail, Integer> {
    public SaleDetailDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SaleDetail.class);
    }

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

    public void updateQuantityItem(String itemName, int orderQty){
        DatabaseManager db = DatabaseManager.getInstance();
        ItemsDao itemsDao = db.getItemDao();

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
