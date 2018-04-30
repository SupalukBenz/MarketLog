package controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import program.ChangePage;
import program.Database;
import program.Items;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Predicate;


public class ItemsController {
    @FXML
    private AnchorPane pane;

    @FXML
    private TextField search;

    @FXML
    private TableView<Items> itemsTable;

    @FXML
    private TableColumn<Items, String> name;

    @FXML
    private TableColumn<Items, String> description;

    @FXML
    private TableColumn<Items, Integer> quantity;

    @FXML
    private TableColumn<Items, Double> price;


    @FXML
    private TableColumn<Items, String> test;

    ObservableList<Items> observableList = FXCollections.observableArrayList();

    private int num = 1;

    @FXML
    public void initialize() {
        readDataToTable();
    }

    public void readDataToTable(){
        ResultSet rs = Database.getAllData("items");
        try {
            while (rs.next()){
                observableList.add(new Items(rs.getString("name_item"), rs.getString("describe_item"),
                        rs.getDouble("price_item"), rs.getInt("quantity"), num++));
            }

        }catch (SQLException se){
            se.printStackTrace();
        }

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("qty"));
        test.setCellValueFactory(new PropertyValueFactory<>("test"));

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
                ObservableList<Items> tableData = FXCollections.observableArrayList();
                ObservableList<TableColumn<Items, ?>> cols = itemsTable.getColumns();

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
        ObservableList<Items> itemsSelected, allItems;
        allItems = itemsTable.getItems();
        itemsSelected = itemsTable.getSelectionModel().getSelectedItems();
        System.out.println(itemsSelected.get(0).getName());
        Database.deleteData("items", "name_item", itemsSelected.get(0).getName());
        itemsSelected.forEach(allItems::remove);

    }



}
