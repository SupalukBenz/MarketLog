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

    @DatabaseField
    private String item;
    @DatabaseField
    private String description;
    @DatabaseField
    private int quantity;
    @DatabaseField
    private double total;
    @DatabaseField
    private int number;

    public Order( String item, String description, int qty, double total, int num){
        this.item = item;
        this.description = description;
        this.quantity = qty;
        this.total = total;
        this.number = num;
    }

    public Order(){

    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public int getNumber() {
        return number;
    }

    public String getDescription(){
        return description;
    }

    public static double getAmount() {
        double amount = 0;
        ResultSet rs = Database.getAllData("orders");
        try{
            while (rs.next()){
                amount += rs.getDouble("total_order");
            }
        } catch (SQLException ex){
            System.out.println("Cannot get amount from database");
        }
        return amount;
    }

    public static int getQtyOrder(){
        int qty = 0;
        ResultSet rs = Database.getAllData("orders");
        try{
            while (rs.next()){
                qty += rs.getDouble("qty_order");
            }
        } catch (SQLException ex){
            System.out.println("Cannot get amount from database");
        }
        return qty;
    }

    public static double sumVat(){
        double amount = getAmount();
        double vat = (amount*10)/100;
        return vat;
    }

    public static double allTotal(){
        double all = sumVat() + getAmount();
        return Math.round(all);
    }

}
