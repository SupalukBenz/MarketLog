package program;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;


public class Sale{

    @FXML
    private AnchorPane pane;


    @FXML
    public void initialize() {

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
