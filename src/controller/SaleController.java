package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import program.*;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class SaleController{

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Sales> tableSale;

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

    @FXML
    private TextField search;



    ObservableList<Sales> observableList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        readDataToTable();
    }

    public void readDataToTable(){
        ResultSet rs = Database.getAllData("sales");
        int num = 0;
        try {
            while (rs.next()){
                observableList.add(new Sales(rs.getString("date_sale"), rs.getInt("receipt_id"), rs.getString("company"),
                        rs.getInt("qty_sale"), rs.getDouble("total_sale"), rs.getString("status_sale")));
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

        tableSale.setItems(null);
        tableSale.setItems(observableList);

    }

    @FXML
    private void handleAddOrder(ActionEvent event){
        ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
    }

    @FXML
    private void filter(KeyEvent key){
        Sales.initFilter(search, tableSale, observableList);
    }


    @FXML
    private void handleUpdateStatus(){
        ChangePage.changeUI("UI/SaleStatusUI.fxml", pane);
    }

    @FXML
    private void handleShowDetail(){

    }
}
