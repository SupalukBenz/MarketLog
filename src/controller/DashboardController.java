package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import program.Database;

import java.sql.ResultSet;
import java.sql.SQLException;


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
        ResultSet rs = Database.getAllData("sales");

        try {
            while(rs.next()){
                if(rs.getString("status_sale").equals("paid")) {
                    saleOrderPaid++;
                    total += rs.getDouble("total_sale");
                }
                if(rs.getString("status_sale").equals("unpaid")) {
                    saleOrderUnpaid++;
                    unpaid += rs.getDouble("total_sale");

                }
                saleOrder++;
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public void getItemsTotal(){
        ResultSet rs = Database.getAllData("items");
        try {
            while(rs.next()){
                if(rs.getInt("quantity_item") == 0){
                    stockItemZero++;
                }else {
                    stockItem++;

                }
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }
}
