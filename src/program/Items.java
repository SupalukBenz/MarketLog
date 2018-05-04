package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import javafx.beans.property.*;

/**
 *
 *
 * @author Supaluk
 */
@DatabaseTable(tableName = "items")
public class Items {
	// every database table needs a primary key
	// by convention it is called "id" and usually an object
	@DatabaseField(generatedId=true)
	private int id;

//    private final IntegerProperty num;
	@DatabaseField
    private String name;
	@DatabaseField
    private String description;
	@DatabaseField
    private double price;
	@DatabaseField
    private int quantity;
	// not in the database
    private int test;
  
    /**
	 * @param name
	 * @param description
	 * @param price
	 * @param qty
	 * @param test
	 */
	public Items(String name, String description, double price, int qty, int test) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = qty;
		this.test = test;
	}
	
	Items() {
		// no args constructor for OrmLite
	}

	public String getDescription() {
        return description;
    }
	  

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }


    public int getQuantity() {
        return quantity;
    }

    public int getTest(){ return test; }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item id " + id + ": " + name;
	}
}
