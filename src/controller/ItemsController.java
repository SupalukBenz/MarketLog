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
import program.ChangePage;
import program.Database;
import program.EditValue;
import program.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class ItemsController {
    @FXML
    private AnchorPane pane;

    @FXML
    private TextField search;

    @FXML
    private TableView<Item> itemsTable;

    @FXML
    private TableColumn<Item, String> name;

    @FXML
    private TableColumn<Item, String> description;

    @FXML
    private TableColumn<Item, Integer> quantity;

    @FXML
    private TableColumn<Item, Double> price;

    @FXML
    private TableColumn<Item, String> number;

    @FXML
    private TableColumn<Item, String> id;

    private int numberTable = 1;

    ObservableList<Item> observableList = FXCollections.observableArrayList();

    private DatabaseManager db;
    private ItemsDao itemsDao = null;

    @FXML
    public void initialize() throws SQLException {
        db = DatabaseManager.getInstance();
        itemsDao = db.getItemDao();
        readDataToTable();
    }


    public void readDataToTable() throws SQLException{
        ResultSet rs = Database.getAllData("items");
        try {
            while (rs.next()){
                observableList.add(new Item(numberTable++, rs.getInt("id_item"), rs.getString("name_item"), rs.getString("description_item"),
                        rs.getDouble("total_item"), rs.getInt("quantity_item")));

            }

        }catch (SQLException se){
            se.printStackTrace();
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

    @FXML
    private void handleAddItem(ActionEvent event){
        ChangePage.changeUI("UI/AddItemUI.fxml", pane);
    }

    @FXML
    private void filter(KeyEvent key){
        intiFilter();
    }

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

    @FXML
    private void handleDeleteItem(){
        if(checkClickTable(itemsTable)) {
            ObservableList<Item> itemsSelected, allItems;
            allItems = itemsTable.getItems();
            itemsSelected = itemsTable.getSelectionModel().getSelectedItems();
            System.out.println("id item delete " + itemsSelected.get(0).getId_item());
//            Database.deleteData("items", "name_item", itemsSelected.get(0).getName());
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

    private void addStock(int result, String item){
//        EditValue.updateStock(item, result);
        List<Item> itemUpdateList = itemsDao.searchByColumnName("name_item", item);
        Item itemUpdate = itemUpdateList.get(0);
        System.out.println("update :" + itemUpdate.getId_item());
        int newqty = itemUpdate.getQuantity_item() + result;
        try {
            itemsDao.update(new Item(itemUpdate.getId_item(), itemUpdate.getName_item(), itemUpdate.getDescription_item(), itemUpdate.getTotal_item(), newqty));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
