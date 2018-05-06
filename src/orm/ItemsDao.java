package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Item;

import java.sql.SQLException;
import java.util.List;

public class ItemsDao extends BaseDaoImpl<Item, Integer> {

    public ItemsDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Item.class);
    }

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

    public int getLastIdItem(Iterable<Item> dao){
        int lastId = 0;
        for(Item item: dao){
            lastId++;
        }
        return lastId;
    }

    public Item getItemFromKey(String tableColumn, String key){
        List<Item> getItem = searchByColumnName(tableColumn, key);
        return getItem.get(0);
    }

}
