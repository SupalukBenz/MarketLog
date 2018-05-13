package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.ItemsDao;
import program.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


/**
 * ItemsController is class for controller managing items and showing items.
 *
 * @author Supaluk Jaroensuk
 */
public class ItemsController {

    /**
     * AnchorPane of UI
     */
    @FXML
    private AnchorPane pane;

    /**
     * Textfield for adding text that user want to search.
     */
    @FXML
    private TextField search;

    /**
     * TableView, showing all of items in data.
     */
    @FXML
    private TableView<Item> itemsTable;

    /**
     * TableColumn for showing name of item.
     */
    @FXML
    private TableColumn<Item, String> name;

    /**
     * TableColumn for showing description of item.
     */
    @FXML
    private TableColumn<Item, String> description;

    /**
     * TableColumn for showing quantity of item.
     */
    @FXML
    private TableColumn<Item, Integer> quantity;

    /**
     * TableColumn for showing price of item.
     */
    @FXML
    private TableColumn<Item, Double> price;

    /**
     * TableColumn for showing number of item.
     */
    @FXML
    private TableColumn<Item, String> number;

    /**
     * TableColumn for showing id of item.
     */
    @FXML
    private TableColumn<Item, String> id;

    /**
     * Total item in table.
     */
    private int numberTable = 0;

    /**
     * ObservableList of item data
     */
    ObservableList<Item> observableList = FXCollections.observableArrayList();

    /**
     * DatabaseManager class
     */
    private DatabaseManager db;

    /**
     * ItemsDao access object for handle all database operation.
     */
    private ItemsDao itemsDao = null;

    /**
     * Initialize DatabaseManager for create ItemsDao and show data in table.
     */
    @FXML
    public void initialize()  {
        db = DatabaseManager.getInstance();
        itemsDao = db.getItemDao();
        readDataToTable();
    }

    /**
     * Read items data to table.
     */
    public void readDataToTable() {
        for(Item item: itemsDao){
            numberTable++;
            item.setNumber(numberTable);
            observableList.add(item);
        }
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        id.setCellValueFactory(new PropertyValueFactory<>("id_item"));
        name.setCellValueFactory(new PropertyValueFactory<>("name_item"));
        description.setCellValueFactory(new PropertyValueFactory<>("description_item"));
        price.setCellValueFactory(new PropertyValueFactory<>("total_item"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity_item"));

        itemsTable.setItems(null);
        itemsTable.setItems(observableList);

    }

    /**
     * Handle add item button
     * @param event is action on button.
     */
    @FXML
    private void handleAddItem(ActionEvent event){
        ChangePage.changeUI("UI/AddItemUI.fxml", pane);
    }

    /**
     * Filter table for searching.
     * @param key is action on textfield.
     */
    @FXML
    private void filter(KeyEvent key){
        intiFilter();
    }

    /**
     * Get text that user want to search and then filter table for searching.
     */
    private void intiFilter(){
        search.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(search.textProperty().get().isEmpty()){
                    itemsTable.setItems(observableList);
                    return;
                }
                ObservableList<Item> tableData = FXCollections.observableArrayList();
                ObservableList<TableColumn<Item, ?>> cols = itemsTable.getColumns();

                for(int i=0; i<observableList.size(); i++){

                    for(int j=0; j<cols.size(); j++){
                        TableColumn col = cols.get(j);
                        String value = col.getCellData(observableList.get(i)).toString();
                        value = value.toLowerCase();
                        if(value.contains(search.textProperty().get().toLowerCase())){
                            tableData.add(observableList.get(i));
                            break;
                        }
                    }
                }
                itemsTable.setItems(tableData);
            }
        });
    }

    /**
     * Handle delete item.
     */
    @FXML
    private void handleDeleteItem(){
        if(checkClickTable(itemsTable)) {
            ObservableList<Item> itemsSelected, allItems;
            allItems = itemsTable.getItems();
            itemsSelected = itemsTable.getSelectionModel().getSelectedItems();
            try {
                //delete from database.
                itemsDao.deleteById(itemsSelected.get(0).getId_item());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //delete from tableView.
            itemsSelected.forEach(allItems::remove);
        }
        ChangePage.changeUI("UI/ItemsUI.fxml", pane);

    }

    /**
     * Check that user select item on table or not.
     * @param table is TableView item.
     * @return true if user select item, false if user not select item.
     */
    private boolean checkClickTable(TableView<?> table){
        if(table.getSelectionModel().getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please, choose item list in table.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Handle update stock of item.
     */
    @FXML
    private void handleUpdateItem(){
        if(checkClickTable(itemsTable)) {
            List<Item> itemForAdd = itemsTable.getSelectionModel().getSelectedItems();
            String item = itemForAdd.get(0).getName_item();
            inputDialog(item);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Added stock");
            alert.setHeaderText(null);
            alert.setContentText("Adding stock, Successful!");

            alert.showAndWait();

            ChangePage.changeUI("UI/ItemsUI.fxml", pane);
        }

    }

    /**
     * Handel saving list of items to pdf file.
     */
    @FXML
    private void handleSavePDF(){
        Report.saveItemsToPDF(itemsDao);
    }

    /**
     * Dialog that was showed when user want to add stock of item.
     * @param item is name of item that want to add stock.
     */
    private void inputDialog(String item){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Add Stock");
        dialog.setHeaderText(item);
        dialog.setContentText("Enter stock:");

        Optional<String> result = dialog.showAndWait();
        if(!result.get().trim().matches("^[\\d]+$") || result.get().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot add stock.");
            alert.setContentText("Invalid value or Incomplete information.");

            alert.showAndWait();
        }else {
            int qty = Integer.parseInt(result.get());
            addStock(qty, item);
        }
    }

    /**
     * Add new quantity of stock to database.
     * @param result is quantity that was added.
     * @param item is name of item.
     */
    private void addStock(int result, String item){
        Item itemUpdate = itemsDao.getItemFromKey("name_item", item);
        System.out.println("update :" + itemUpdate.getId_item());
        int newqty = itemUpdate.getQuantity_item() + result;
        try {
            itemsDao.update(new Item(itemUpdate.getId_item(), itemUpdate.getName_item(), itemUpdate.getDescription_item(), itemUpdate.getTotal_item(), newqty));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
