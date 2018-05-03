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
import program.*;
import sun.jvm.hotspot.memory.EdenSpace;

import java.awt.event.ActionEvent;
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

    @FXML
    private void initialize(){
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
        ObservableList getItemId = Database.searchFromKeyId("sale_id_details", "id_detail", id, "item_detail");
        ObservableList getQtyId = Database.searchFromKeyId("sale_id_details", "id_detail", id, "qty_detail");
        for(int i=0; i<=getItemId.size()-1; i++){
            System.out.println("getItemID : " + getItemId.get(i).toString());
            System.out.println("getQty : " + Integer.parseInt(getQtyId.get(i).toString()));
            EditValue.editingQtyOfItems(getItemId.get(i).toString(),Integer.parseInt(getQtyId.get(i).toString()));
        }
    }

    private void changeStatus(){
        int receiptId = salesList.get(0).getReceiptId();
        listOfItem(receiptId);
        Database.updateData("sales", "status_sale" ,"paid" , "receipt_id", receiptId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update status");
        alert.setHeaderText("Order: " + receiptId + "\n" + salesList.get(0).getCompany());
        alert.setContentText("Paid, Successful!");

        alert.showAndWait();

        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }

    private void readDataToTable(){
        ResultSet rs = Database.getAllData("sales");
        try {
            while (rs.next()){

                if(rs.getString("status_sale").equals("unpaid")) {
                    observableList.add(new Sales(rs.getString("date_sale"), rs.getInt("receipt_id"), rs.getString("company"),
                            rs.getInt("qty_sale"), rs.getDouble("total_sale"), rs.getString("status_sale")));
                }
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        receiptId.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
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
