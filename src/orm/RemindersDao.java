package orm;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import program.Reminders;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO class of reminders which implements Base class for the Database Access Objects
 * that handle the reading and writing a class from the database.
 *
 * @author Supaluk Jaroensuk
 */
public class RemindersDao extends BaseDaoImpl<Reminders, Integer> {
    /**
     * Total list of reminder
     */
    private int size = 0;

    /**
     * Initialize DAO class for handle all database operations.
     * @param connectionSource A reduction of the SQL DataSource.
     * @throws SQLException an exception that provides information on a database access error or other errors.
     */
    public RemindersDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, Reminders.class);
    }

    /**
     * Search data in database and store in list.
     * @param tableColumn is column name in table.
     * @param search is key for search.
     * @return list of reminders that user searched.
     */
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

    /**
     * Search data in database and then store in list.
     * @param tableColumn is column name in table.
     * @param key is key for search.
     * @return Reminders of list.
     */
    public Reminders getEventFromKey(String tableColumn, int key){
        List<Reminders> getEvent = searchByColumnName(tableColumn, key);
        return getEvent.get(0);
    }

    /**
     * Get the latest id of data from database.
     * @param remindersDao is ReminderDao.
     * @return total list of data.
     */
    public int getPreviousId(RemindersDao remindersDao){
        for(Reminders reminders: remindersDao){
            size++;
        }
        return size;
    }
}
