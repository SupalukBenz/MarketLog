package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import orm.DatabaseManager;
import orm.ItemsDao;
import orm.SalesDao;
import program.Item;
import program.Sales;

/**
 * DashboardController is class for controller dashboardUI that showing detail of sales and items stock.
 *
 * @author Supaluk Jaroensuk
 */
public class DashboardController {

    /**
     * AnchorPane of UI
     */
    @FXML
    private AnchorPane pane;

    /**
     * Text label each sales and items stock detail.
     */
    @FXML
    private Label saleAmount, currentStock, zeroStock, totalOrder, unpaidOrder, paidOrder, unpaidOrderTotal;

    /**
     * ProgressIndicator for show comparing between order paid and unpaid.
     */
    @FXML
    private ProgressIndicator totalPayment;

    /**
     * DatabaseManager class
     */
    private DatabaseManager db;

    /**
     * SalesDao access object for handle all database operation.
     */
    private SalesDao salesDao = null;

    /**
     * Item data access object for handle all database operation.
     */
    private ItemsDao itemsDao = null;

    /**
     * Total of sales order
     */
    private double total = 0;

    /**
     * Total stock item
     */
    private int stockItem = 0;

    /**
     * Total item that stock is zero.
     */
    private int stockItemZero = 0;

    /**
     * Total sales order
     */
    private int saleOrder = 0;

    /**
     * Total sales order that paid status.
     */
    private int saleOrderPaid = 0;

    /**
     * Total sales order that unpaid status.
     */
    private int saleOrderUnpaid = 0;

    /**
     * Total unpaid order
     */
    private double unpaid = 0;

    /**
     * Initialize DatabaseManager for create SalesDao and set value to showing.
     */
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

    /**
     * Calculate payment for showing in ProgressIndicator.
     * @return
     */
    private double calculatePayment(){
        double totalPaid = saleOrderPaid - saleOrderUnpaid;

        return (totalPaid/saleOrderPaid);
    }

    /**
     * Get total sales order.
     */
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

    /**
     * Get total item stock.
     */
    public void getItemsTotal(){
        for(Item item: itemsDao){
            if(item.getQuantity_item() == 0) stockItemZero++;
            else stockItem++;

        }
    }
}
