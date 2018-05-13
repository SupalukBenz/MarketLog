package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Item is class that persist to SQL database.
 *
 * @author Supaluk
 */
@DatabaseTable(tableName = "items")
public class Item {

    /**
     * Column of items table that store name item.
     */
    @DatabaseField
    private String name_item;

    /**
     * Column of items table that store description item.
     */
    @DatabaseField
    private String description_item;

    /**
     * Column of items table that store total item.
     */
    @DatabaseField
    private double total_item;

    /**
     * Column of items table that store quantity item.
     */
    @DatabaseField
    private int quantity_item;

    /**
     * Column of items table that store id item.
     */
    @DatabaseField(id=true)
    private int id_item;

    /**
     * Count list of data in database.
     */
    private int number;

    /**
     * Initialize details of item.
     * @param id is id of item.
     * @param name is name of item.
     * @param description is description of item.
     * @param price is price of item.
     * @param qty is quantity of item.
     */
    public Item(int id, String name, String description, double price, int qty) {
        super();
        this.id_item = id;
        this.name_item = name;
        this.description_item = description;
        this.total_item = price;
        this.quantity_item = qty;
    }

    /**
     * Initialize details of item with number.
     * @param number is number for show in total item table.
     * @param id is id of item.
     * @param name is name of item.
     * @param description is description of item.
     * @param price is price of item.
     * @param qty is quantity of item.
     */
    public Item(int number, int id, String name, String description, double price, int qty) {
        super();
        this.number = number;
        this.id_item = id;
        this.name_item = name;
        this.description_item = description;
        this.total_item = price;
        this.quantity_item = qty;
    }

    /**
     * no-arg constructor
     */
    public Item(){

    }

    /**
     * Set id of item.
     * @param id_item is id for setting.
     */
    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    /**
     * Set number of item.
     * @param number is number for setting.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get number of item.
     * @return number of item.
     */
    public int getNumber(){ return number; }

    /**
     * Get name of item.
     * @return name of item.
     */
    public String getName_item() {
        return name_item;
    }

    /**
     * Get description of item.
     * @return description of item.
     */
    public String getDescription_item() {
        return description_item;
    }

    /**
     * Get total of item.
     * @return total of item.
     */
    public double getTotal_item() {
        return total_item;
    }

    /**
     * Get quantity of item.
     * @return quantity of item.
     */
    public int getQuantity_item() {
        return quantity_item;
    }

    /**
     * Get id of item.
     * @return id of items.
     */
    public int getId_item() {
        return id_item;
    }
}
