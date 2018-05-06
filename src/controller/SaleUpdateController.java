package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.ItemsDao;
import orm.SaleDetailDao;
import orm.SalesDao;
import program.*;
import sun.jvm.hotspot.memory.EdenSpace;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SaleUpdateController {

    @FXML
    AnchorPane pane;

    @FXML
    private TextField search;

    @FXML
    private TableView<Sales> tableSaleStatus;

    @FXML
    private TableColumn<Sales, String> date;

    @FXML
    private TableColumn<Sales, Integer> receiptId;

    @FXML
    private TableColumn<Sales, String> company;

    @FXML
    private TableColumn<Sales, Integer> qty;

    @FXML
    private TableColumn<Sales, Double> total;

    @FXML
    private TableColumn<Sales, String> status;

    private ObservableList<Sales> observableList = FXCollections.observableArrayList();
    private ObservableList<Sales> salesList = FXCollections.observableArrayList();

    private DatabaseManager db;
    private SalesDao salesDao = null;
    private SaleDetailDao saleDetailDao = null;

    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        salesDao = db.getSalesDao();
        saleDetailDao = db.getSaleDetailDao();
        readDataToTable();
    }

    @FXML
    private void handleUpdate(){
        if(tableSaleStatus.getSelectionModel().getSelectedItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please, choose order list in table.");
            alert.showAndWait();
        }else {
            salesList = tableSaleStatus.getSelectionModel().getSelectedItems();
            changeStatus();
        }

    }

    private void listOfItem(int id){
//        ObservableList getItemId = Database.searchFromKeyId("sale_id_details", "id_detail", id, "item_detail");
//        ObservableList getQtyId = Database.searchFromKeyId("sale_id_details", "id_detail", id, "qty_detail");
//        for(int i=0; i<=getItemId.size()-1; i++){
//            System.out.println("getItemID : " + getItemId.get(i).toString());
//            System.out.println("getQty : " + Integer.parseInt(getQtyId.get(i).toString()));
////            EditValue.editingQtyOfItems(getItemId.get(i).toString(),Integer.parseInt(getQtyId.get(i).toString()));
//
//        }
        List<SaleDetail> getIdDetail = saleDetailDao.searchByColumnName("id_detail", id);
        for(SaleDetail saleDetail : getIdDetail){
            saleDetailDao.updateQuantityItem(saleDetail.getItem_detail(), saleDetail.getQty_detail());
        }

    }

    private void changeStatus(){
        int receiptId = salesList.get(0).getReceiptId();
        System.out.println("receipt id : " + receiptId);
        listOfItem(receiptId);
        Sales sale = salesDao.getUpdateUnpaidFromId(receiptId);
        try {
            salesDao.update(new Sales(sale.getDate(), sale.getReceiptId(), sale.getCompany(), sale.getQuantity(), sale.getTotal(), "paid"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        Database.updateData("sales", "status_sale" ,"paid" , "receipt_id", receiptId);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update status");
        alert.setHeaderText("Order: " + receiptId + "\n" + salesList.get(0).getCompany());
        alert.setContentText("Paid, Successful!");

        alert.showAndWait();

        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }

    private void readDataToTable(){
//        ResultSet rs = Database.getAllData("sales");
//        try {
//            while (rs.next()){
//
//                if(rs.getString("status_sale").equals("unpaid")) {
//                    observableList.add(new Sales(rs.getString("date_sale"), rs.getInt("receipt_id"), rs.getString("company"),
//                            rs.getInt("qty_sale"), rs.getDouble("total_sale"), rs.getString("status_sale")));
//                }
//            }
//        }catch (SQLException se){
//            se.printStackTrace();
//        }
        List<Sales> unpaidList = salesDao.searchByColumnName("status_sale", "unpaid");
        for(Sales sales: unpaidList){
            observableList.add(sales);
        }
        receiptId.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableSaleStatus.setItems(null);
        tableSaleStatus.setItems(observableList);

    }

    @FXML
    private void filter(KeyEvent key){
        Sales.initFilter(search, tableSaleStatus, observableList);
    }


    @FXML
    private void handleBackButton(){
        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }
}
