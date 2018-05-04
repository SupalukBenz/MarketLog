package marketlog.orm;

import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import program.Items;

/**
 * A data access object for Items.
 * @see Ormlite Documentation
 *
 */
public class ItemsDao extends BaseDaoImpl<Items, Integer> {
	// super: ConnectionSource connectionSource;

	public ItemsDao(ConnectionSource connection) throws SQLException {
		super(connection, Items.class);
	}
	
	public List<Items> searchByName(String findname) {
		QueryBuilder<Items, Integer> qb = this.queryBuilder();
		List<Items> result = null;
		try {
			result = qb.where().like("name", "%"+findname+"%").query();
		} catch (SQLException e) {
			// TODO log the error
			e.printStackTrace();
		}
		return result;
	}
	
}
