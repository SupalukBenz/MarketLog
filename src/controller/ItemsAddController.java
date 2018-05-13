package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.ItemsDao;
import program.ChangePage;
import program.Item;

import java.sql.SQLException;

/**
 * ItemAddController is class for controller adding item.
 */
public class ItemsAddController {

    /**
     * AnchorPane of UI
     */
    @FXML
    AnchorPane pane;

    /**
     * Textfield for receiving data.
     */
    @FXML
    private TextField nameAdd, qtyAdd, descriptionAdd, priceAdd;

    /**
     * Button for back to item page.
     */
    @FXML
    private Button backButton;

    /**
     * Name of item that user want to add.
     */
    private String nameItem, descriptionItem;

    /**
     * Price of item that user want to add.
     */
    private double priceItem;

    /**
     * Quantity of item that user want to add.
     */
    private int qtyItem;

    @FXML
    public void initialize(){

    }

    /**
     * Handle adding item
     */
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

    /**
     * Check textfield that empty or not.
     * @return true if textfield is not empty, and false if textfield is empty.
     */
    private boolean isCheck(){
        if(!nameAdd.getText().trim().isEmpty() && !qtyAdd.getText().trim().isEmpty() && !descriptionAdd.getText().trim().isEmpty()
                && !priceAdd.getText().trim().isEmpty()) return true;
        return false;
    }

    /**
     * Handle backing to item page.
     */
    @FXML
    private void handleBackToItems(){
        ChangePage.changeUI("UI/ItemsUI.fxml", pane);
    }

    /**
     * Check that quantity and price were added be integer or not.
     * @return true if quantity and price are integer, false if quantity and price are not integer.
     */
    private boolean checkInteger(){
        if(qtyAdd.getText().trim().matches("^[\\d]+$") && priceAdd.getText().trim().matches("^[\\d]+$")) return true;
        return false;
    }

    /**
     * Adding detail of item to database.
     */
    private void addToDatabase() {
        nameItem = nameAdd.getText();
        descriptionItem = descriptionAdd.getText();
        qtyItem = Integer.parseInt(qtyAdd.getText());
        priceItem = Double.parseDouble(priceAdd.getText());
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        ItemsDao itemsDao = databaseManager.getItemDao();
        Item item = new Item(itemsDao.getLastIdItem(itemsDao)+1, nameItem, descriptionItem, priceItem, qtyItem);
        try {
            itemsDao.create(item);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle clearing data in textfield.
     */
    @FXML
    private void handleClearButton(){
        nameAdd.clear();
        priceAdd.clear();
        descriptionAdd.clear();
        qtyAdd.clear();
    }

}
