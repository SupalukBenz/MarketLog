package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import program.ChangePage;
import program.Sales;

public class SaleUpdateController {

    @FXML
    AnchorPane pane;

    @FXML
    private TextField search;

    @FXML
    private TableView<Sales> tableSaleStatus;

    private ObservableList<Sales> observableList = FXCollections.observableArrayList();
    @FXML
    private void handleUpdate(){

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
