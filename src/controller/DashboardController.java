package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import orm.DatabaseManager;
import orm.ItemsDao;
import orm.SalesDao;
import program.Database;
import program.Item;
import program.Sales;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DashboardController {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label saleAmount, currentStock, zeroStock, totalOrder, unpaidOrder, paidOrder, unpaidOrderTotal;

    @FXML
    private ProgressIndicator totalPayment;

    private DatabaseManager db;
    private SalesDao salesDao = null;
    private ItemsDao itemsDao = null;

    private double total = 0;
    private int stockItem = 0;
    private int stockItemZero = 0;

    private int saleOrder = 0;
    private int saleOrderPaid = 0;
    private int saleOrderUnpaid = 0;
    private double unpaid = 0;

    @FXML
    public void initialize() {
        db = DatabaseManager.getInstance();
        salesDao = db.getSalesDao();
        itemsDao = db.getItemDao();

        getItemsTotal();
        getSalesTotal();
        saleAmount.setText(String.valueOf(total));
        currentStock.setText(String.valueOf(stockItem));
        zeroStock.setText(String.valueOf(stockItemZero));
        totalOrder.setText(String.valueOf(saleOrder));
        unpaidOrder.setText(String.valueOf(saleOrderUnpaid));
        unpaidOrder.setTextFill(Color.valueOf("#ff8080"));
        paidOrder.setText(String.valueOf(saleOrderPaid));
        paidOrder.setTextFill(Color.valueOf("#8cd98c"));
        unpaidOrderTotal.setText(String.valueOf(unpaid));
        unpaidOrderTotal.setTextFill(Color.valueOf("#ff8080"));

        totalPayment.setProgress(calculatePayment());
    }

    private double calculatePayment(){
        double totalPaid = saleOrderPaid - saleOrderUnpaid;

        return (totalPaid/saleOrderPaid);
    }

    public void getSalesTotal(){

        for(Sales sales: salesDao){
            if(sales.getStatus().equals("paid")){
                saleOrderPaid++;
                total += sales.getTotal();
            }
            if(sales.getStatus().equals("unpaid")){
                saleOrderUnpaid++;
                unpaid += sales.getTotal();
            }
            saleOrder++;
        }
    }

    public void getItemsTotal(){
        for(Item item: itemsDao){
            if(item.getQuantity_item() == 0) stockItemZero++;
            else stockItem++;

        }
    }
}
