package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import program.ChangePage;


public class HomePageController {


    @FXML
    BorderPane pane;


    @FXML
    private Button dashboardButton, reportButton, itemsButton, saleButton, calendarButton, helpButton;

    @FXML
    public void initialize(){
        dashboardButton.fire();
    }

    @FXML
    private void dashboard(ActionEvent e){
        initializeColorBotton();
        changeColorButton(dashboardButton);
        ChangePage.loadUI("DashboardUI", pane, HomePageController.class);

    }

    @FXML
    private void items(ActionEvent e){
        initializeColorBotton();
        changeColorButton(itemsButton);
    }

    @FXML
    private void report(ActionEvent e){
        initializeColorBotton();
        changeColorButton(reportButton);
        ChangePage.loadUI("ReportUI", pane, HomePageController.class);
    }

    @FXML
    private void sale(ActionEvent e){
        initializeColorBotton();
        changeColorButton(saleButton);
        ChangePage.loadUI("SaleUI", pane, HomePageController.class);
    }

    @FXML
    private void calendar(ActionEvent e){
        initializeColorBotton();
        changeColorButton(calendarButton);
    }

    @FXML
    private void help(ActionEvent e){
        initializeColorBotton();
        changeColorButton(helpButton);
    }

    private void initializeColorBotton(){
        dashboardButton.setStyle("-fx-background-color:  #2d5986");
        reportButton.setStyle("-fx-background-color:  #2d5986");
        saleButton.setStyle("-fx-background-color:  #2d5986");
        itemsButton.setStyle("-fx-background-color:  #2d5986");
        helpButton.setStyle("-fx-background-color:  #2d5986");
        calendarButton.setStyle("-fx-background-color:  #2d5986");
    }

    private void changeColorButton(Button button){
        button.setStyle("-fx-background-color: #4da6ff");
    }

}
