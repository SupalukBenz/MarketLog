package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import program.ChangePage;
import program.Database;
import program.Items;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ItemsController implements Initializable{
    @FXML
    private AnchorPane pane;

    @FXML
    private TextField search;

    @FXML
    private TextField nameAdd, priceAdd, qtyAdd, descriotionAdd;

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
    private TableColumn<Items, Integer> number;

    @FXML
    private Button addButton;
    ObservableList<Items> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       readDataToTable();
    }


    @FXML
    public void initialize(){

    }

    public void readDataToTable(){
        ResultSet rs = Database.getAllData("items");
        int num = 0;
        try {

            while (rs.next()){

                observableList.add(new Items(num++, rs.getString("name_item"), rs.getString("describe_item"),
                        rs.getDouble("price_item"), rs.getInt("quantity")));

            }

        }catch (SQLException se){
            se.printStackTrace();
        }


        number.setCellValueFactory(new PropertyValueFactory<>("num"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("qty"));


        itemsTable.setItems(observableList);
    }

    @FXML
    private void handleAddItem(ActionEvent event){
        ChangePage.changeUI("UI/SaleUI.fxml", pane);

    }

    @FXML
    private void handleAddButton(){

    }

    @FXML
    private void handleBackToItems(){
//        ChangePage.changeUI("UI/ItemsUI.fxml", pane);
    }



    private void initFilter(){

    }

}
