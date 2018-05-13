package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import program.ChangePage;

/**
 * HomePageController is class for controller homepage of program.
 *
 * @author Supaluk Jaroensuk
 */
public class HomePageController {

    /**
     * BorderPane of UI
     */
    @FXML
    BorderPane pane;

    /**
     * Button on UI
     */
    @FXML
    private Button dashboardButton, chartButton, itemsButton, saleButton, calendarButton, helpButton;

    /**
     * Initialize homepage by showing first page of program.
     */
    @FXML
    public void initialize(){
        dashboardButton.fire();
    }

    /**
     * Action on dashboard button for showing dashboard page.
     * @param e is action on button.
     */
    @FXML
    private void dashboard(ActionEvent e){
        initializeColorBotton();
        changeColorButton(dashboardButton);
        ChangePage.loadUI("DashboardUI", pane, HomePageController.class);

    }

    /**
     * Action on item button for showing item page.
     * @param e is action on button.
     */
    @FXML
    private void items(ActionEvent e){
        initializeColorBotton();
        changeColorButton(itemsButton);
        ChangePage.loadUI("ItemsUI", pane, HomePageController.class);
    }

    /**
     * Action on chart button for showing chart page.
     * @param e is action on button.
     */
    @FXML
    private void chart(ActionEvent e){
        initializeColorBotton();
        changeColorButton(chartButton);
        ChangePage.loadUI("ChartUI", pane, HomePageController.class);
    }

    /**
     * Action on sale button for showing sales page.
     * @param e is action on button.
     */
    @FXML
    private void sale(ActionEvent e){
        initializeColorBotton();
        changeColorButton(saleButton);
        ChangePage.loadUI("SaleUI", pane, HomePageController.class);
    }

    /**
     * Action on calendar button for showing event page.
     * @param e is action on button.
     */
    @FXML
    private void calendar(ActionEvent e){
        initializeColorBotton();
        changeColorButton(calendarButton);
        ChangePage.loadUI("ReminderUI", pane, HomePageController.class);
    }

    /**
     * Action on help button for showing help page.
     * @param e is action on button.
     */
    @FXML
    private void help(ActionEvent e){
        initializeColorBotton();
        changeColorButton(helpButton);
    }

    /**
     * Initialize color of button.
     */
    private void initializeColorBotton(){
        dashboardButton.setStyle("-fx-background-color:  #2d5986");
        chartButton.setStyle("-fx-background-color:  #2d5986");
        saleButton.setStyle("-fx-background-color:  #2d5986");
        itemsButton.setStyle("-fx-background-color:  #2d5986");
        helpButton.setStyle("-fx-background-color:  #2d5986");
        calendarButton.setStyle("-fx-background-color:  #2d5986");
    }

    /**
     * Change color of button when click on it.
     * @param button button that clicking.
     */
    private void changeColorButton(Button button){
        button.setStyle("-fx-background-color: #4da6ff");
    }

}
