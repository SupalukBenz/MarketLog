package program;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final StringProperty item;
    private final StringProperty description;
    private final IntegerProperty quantity;
    private final DoubleProperty total;
    private final IntegerProperty number;



    public Order( String item, String description, int qty, double total, int num){
        this.item = new SimpleStringProperty(item);
        this.description = new SimpleStringProperty(description);
        this.quantity = new SimpleIntegerProperty(qty);
        this.total = new SimpleDoubleProperty(total);
        this.number = new SimpleIntegerProperty(num);
    }


    public String getItem() {
        return item.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getTotal() {
        return total.get();
    }

    public int getNumber() {
        return number.get();
    }

    public String getDescription(){
        return description.get();
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
