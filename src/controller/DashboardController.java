package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import program.Dashboard;
import program.Database;


public class DashboardController {
    @FXML
    private AnchorPane pane;

    @FXML
    private Label saleAmount, currentStock, zeroStock, totalOrder, unpaidOrder, paidOrder, unpaidOrderTotal;

    @FXML
    private ProgressIndicator totalPayment;

    private double total = 0;
    private int stockItem = 0;
    private int stockItemZero = 0;

    private int saleOrder = 0;
    private int saleOrderPaid = 0;
    private int saleOrderUnpaid = 0;
    private double unpaid = 0;

    @FXML
    public void initialize() {
        Dashboard.getSalesTotal();
        Dashboard.getItemsTotal();

        total = Dashboard.getTotalSale();
        saleOrder = Dashboard.getOrderTotal();
        saleOrderPaid = Dashboard.getOrderPaid();
        saleOrderUnpaid = Dashboard.getOrderUnpaid();
        stockItem = Dashboard.getItemsStock();
        stockItemZero = Dashboard.getItemStockZero();
        unpaid = Dashboard.getUnpaidTotal();

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
    }
}
