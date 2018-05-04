package marketlog.orm;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import program.Items;

public class DaoTest {
	private static void showAllItems(String message, Iterable<Items> dao) {
		// print all items
		System.out.println(message);
		for(Items item: dao) {
			System.out.printf("%d  %-30.30s  %.2f %d\n", 
					item.getId(), item.getName(), item.getPrice(), item.getQuantity());
		}
	}
	
	public static void main(String[] args) throws SQLException {
		Scanner console = new Scanner(System.in);
		DatabaseManager db = DatabaseManager.getInstance();
		ItemsDao dao = db.getItemsDao();
	
		
		Items item = new Items("Anti-oxidant Green Coffee","per 100g", 210.0, 20, 0);
		dao.create(item);
//		item = new Items("Green Mulberry Leaf Tea","per 100g", 75.0, 20, 0);
//		dao.create(item);
		
		showAllItems("All items in database", dao);
		
		while(true) {
			System.out.print("Enter item to find: ");
			String search = console.nextLine().trim();
			if (search.isEmpty()) break;
			List<Items> result = dao.searchByName(search);
			showAllItems("Items matching your search", result);
		}
		
	}
}
