package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Reminders;

import java.sql.SQLException;
import java.util.List;

public class RemindersDao extends BaseDaoImpl<Reminders, Integer> {

    public RemindersDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Reminders.class);
    }

    public List<Reminders> searchByColumnName(String tableColumn, int search){
        QueryBuilder<Reminders, Integer> qb = this. queryBuilder();
        List<Reminders> orderList = null;

        try {
            orderList = qb.where().eq(tableColumn, search).query();
        } catch (SQLException se){
            System.out.println("Cannot search in reminders data.");
        }

        return orderList;
    }

    public Reminders getEventFromKey(String tableColumn, int key){
        List<Reminders> getEvent = searchByColumnName(tableColumn, key);
        return getEvent.get(0);
    }
}
