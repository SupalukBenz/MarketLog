package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@DatabaseTable (tableName = "orders")
public class Order {

    @DatabaseField(id = true)
    private String item_order;
    @DatabaseField
    private String description_order;
    @DatabaseField
    private int qty_order;
    @DatabaseField
    private double total_order;
    private int number;


    public Order( String item, String description, int qty, double total){
        this.item_order = item;
        this.description_order = description;
        this.qty_order = qty;
        this.total_order = total;
    }

    public Order(){

    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getItem() {
        return item_order;
    }

    public int getQuantity() {
        return qty_order;
    }

    public double getTotal() {
        return total_order;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription(){
        return description_order;
    }


}
