package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Order is class that persist to SQL database.
 *
 * @author Supaluk Jaroensuk
 */
@DatabaseTable (tableName = "orders")
public class Order {

    /**
     * Column of orders table that store name order.
     */
    @DatabaseField(id = true)
    private String item_order;

    /**
     * Column of orders table that store description order.
     */
    @DatabaseField
    private String description_order;

    /**
     * Column of orders table that store quantity order.
     */
    @DatabaseField
    private int qty_order;

    /**
     * Column of orders table that store total order.
     */
    @DatabaseField
    private double total_order;

    /**
     * Count list of data in database
     */
    private int number;

    /**
     * Initialize details of order.
     * @param item is name of item.
     * @param description is description of item.
     * @param qty is quantity of item.
     * @param total is total of item.
     */
    public Order( String item, String description, int qty, double total){
        this.item_order = item;
        this.description_order = description;
        this.qty_order = qty;
        this.total_order = total;
    }

    /**
     * no-arg constructor
     */
    public Order(){

    }

    /**
     * Set number of order.
     * @param number is number of order.
     */
    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * Get name of order.
     * @return name of order.
     */
    public String getItem() {
        return item_order;
    }

    /**
     * Get quantity of order.
     * @return quantity of order.
     */
    public int getQuantity() {
        return qty_order;
    }

    /**
     * Get total of order.
     * @return total of order.
     */
    public double getTotal() {
        return total_order;
    }

    /**
     * Get number of order.
     * @return number of order.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Get description of order.
     * @return description of order.
     */
    public String getDescription(){
        return description_order;
    }


}
