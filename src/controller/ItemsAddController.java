package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import program.ChangePage;
import program.Database;


public class ItemsAddController {

    @FXML
    AnchorPane pane;

    @FXML
    private TextField nameAdd, qtyAdd, descriptionAdd, priceAdd;

    @FXML
    private Button backButton;

    private String nameItem, descriptionItem;
    private double priceItem;
    private int qtyItem;

    @FXML
    public void initialize(){

    }

    @FXML
    private void handleAddButton(){

        if(isCheck() && checkInteger()){
            System.out.println("true");
            addToDatabase();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Added Item");
            alert.setHeaderText(null);
            alert.setContentText("Adding item, Successful!");

            alert.showAndWait();
            backButton.fire();

        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add item.");
            alert.setContentText("Invalid value or Incomplete information.");

            alert.showAndWait();
            System.out.println("false");
        }
    }

    private boolean isCheck(){
        if(!nameAdd.getText().trim().isEmpty() && !qtyAdd.getText().trim().isEmpty() && !descriptionAdd.getText().trim().isEmpty()
                && !priceAdd.getText().trim().isEmpty()) return true;
        return false;
    }

    @FXML
    private void handleBackToItems(){
        ChangePage.changeUI("UI/ItemsUI.fxml", pane);
    }

    private boolean checkInteger(){
        if(qtyAdd.getText().trim().matches("^[\\d]+$") && priceAdd.getText().trim().matches("^[\\d]+$")) return true;
        return false;
    }



    private void addToDatabase(){

        nameItem = nameAdd.getText();
        descriptionItem = descriptionAdd.getText();
        qtyItem = Integer.parseInt(qtyAdd.getText());
        priceItem = Double.parseDouble(priceAdd.getText());

        Database.insertData("items", nameItem, descriptionItem, priceItem, qtyItem);


    }

    @FXML
    private void handleClearButton(){
        nameAdd.clear();
        priceAdd.clear();
        descriptionAdd.clear();
        qtyAdd.clear();
    }

}
