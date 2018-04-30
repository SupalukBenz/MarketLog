package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import program.ChangePage;
import program.Database;
import program.Items;
import program.Sales;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;


public class SaleController implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<Sales> tableSale;

    @FXML
    private TableColumn<Sales, Date> date;

    @FXML
    private TableColumn<Sales, Integer> receiptId;

    @FXML
    private TableColumn<Sales, String> company;

    @FXML
    private TableColumn<Sales, Integer> qty;

    @FXML
    private TableColumn<Sales, Double> total;

    ObservableList<Sales> observableList = FXCollections.observableArrayList();


    @FXML
    public void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        readDataToTable();
    }

    public void readDataToTable(){
        ResultSet rs = Database.getAllData("sales");
        int num = 0;
        try {
            while (rs.next()){
                observableList.add(new Sales(rs.getDate("date_sale"), rs.getInt("receipt_id"), rs.getString("company"),
                        rs.getInt("quantity_sale"), rs.getDouble("total"), rs.getBoolean("status_sale")));
            }
        }catch (SQLException se){
            se.printStackTrace();
        }

        date.setCellValueFactory(new PropertyValueFactory<>(""));


    }

    @FXML
    private void handleAddOrder(ActionEvent event){
        ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
    }

    @FXML
    private void handleBackToSale(ActionEvent event){
        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }

}
