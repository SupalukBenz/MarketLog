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
public class Item {

    @DatabaseField
    private String name_item;
    @DatabaseField
    private String description_item;
    @DatabaseField
    private double total_item;
    @DatabaseField
    private int quantity_item;
    @DatabaseField(id=true)
    private int id_item;

    private int number;

    public Item(int id, String name, String description, double price, int qty) {
        super();
        this.id_item = id;
        this.name_item = name;
        this.description_item = description;
        this.total_item = price;
        this.quantity_item = qty;
    }

    public Item(int number, int id, String name, String description, double price, int qty) {
        super();
        this.number = number;
        this.id_item = id;
        this.name_item = name;
        this.description_item = description;
        this.total_item = price;
        this.quantity_item = qty;
    }

    public Item(){

    }

    public Item(String name, String description, double price, int qty) {
        super();
        this.name_item = name;
        this.description_item = description;
        this.total_item = price;
        this.quantity_item = qty;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber(){ return number; }

    public String getName_item() {
        return name_item;
    }

    public String getDescription_item() {
        return description_item;
    }

    public double getTotal_item() {
        return total_item;
    }

    public int getQuantity_item() {
        return quantity_item;
    }

    public int getId_item() {
        return id_item;
    }
}
