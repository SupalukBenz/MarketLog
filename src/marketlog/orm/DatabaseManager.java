package marketlog.orm;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import program.Items;
import util.PropertyManager;

/**
 * Class responsible for providing access to the database
 * using ORMLite.
 * 
 * @author jim
 */
public class DatabaseManager {
	private static DatabaseManager instance;
	private ConnectionSource connection;
	// a single instance of ItemsDao
	private ItemsDao itemsDao = null;

	protected DatabaseManager() {
		String url = PropertyManager.getProperty("jdbc.url");
		String username = PropertyManager.getProperty("jdbc.user","");
		String password = PropertyManager.getProperty("jdbc.password","");
		try {
			connection = new JdbcConnectionSource(url, username, password);
		} catch (SQLException sqle) {
			System.out.println("Could not connect to database");
			System.out.println(sqle.getMessage());
		}
	}
	
	public static DatabaseManager getInstance() {
		if (instance == null) instance = new DatabaseManager();
		return instance;
	}
	
	public ItemsDao getItemsDao() {
		if (itemsDao == null) try {
			itemsDao = new ItemsDao(connection);
		} catch (SQLException sqle) {
			System.out.println("Could not create ItemsDao");
			System.out.println(sqle.getMessage());
		}
		return itemsDao;
	}
	
	
}
