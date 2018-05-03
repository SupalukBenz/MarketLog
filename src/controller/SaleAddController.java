package controller;


import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import program.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class SaleAddController {

    @FXML
    AnchorPane pane;

    @FXML
    private TextField qtyAdd, total, totalFromQty, amount, vat, companyAdd, descriptionShow;


    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> itemTable;

    @FXML
    private TableColumn<Order, Integer> qtyTable;

    @FXML
    private TableColumn<Order, String> descripTable;

    @FXML
    private TableColumn<Order, Double> totalTable;

    @FXML
    private TableColumn<Order, Integer> numberTable;

    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    @FXML
    private ComboBox listOfItem;

    @FXML
    private ComboBox status;

    @FXML
    private Button backButton;

    private ResultSet rs = null;

    private String get, statusAdd;

    private double totalSave = 0;

    private int num = 1;

    @FXML
    public void initialize() {
        addListToTable();
        orderToTable();
    }

    private void addListToTable(){
        ObservableList observableList = Database.selectItem("items", "name_item");
        listOfItem.getItems().addAll(observableList);
        listOfItem.getSelectionModel().select(0);
        handleSelectItem();
    }

    @FXML
    private void handleSelectItem(){
        get = listOfItem.getValue().toString();
        showDescription(get);
    }

    @FXML
    private void handleSelectStatus(){
        statusAdd = status.getValue().toString();
        System.out.println("status : " + statusAdd);
    }

    @FXML
    private void handleBackToSale(ActionEvent event){
        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }

    private void showDescription(String value){
        ObservableList describe = Database.searchFromKey("items", "name_item", value, "describe_item");
        String result = describe.get(0).toString();
        descriptionShow.setText(result);
    }

    private boolean checkInteger(){
        if(qtyAdd.getText().trim().matches("^[\\d]+$")) return true;
        return false;
    }

    private boolean isCheckAll(){
        if(!total.getText().trim().isEmpty() && !amount.getText().trim().isEmpty() && !vat.getText().trim().isEmpty() && !companyAdd.getText().trim().isEmpty()) return true;
        return false;
    }

    private boolean isCheckForAdd(){
        if(!qtyAdd.getText().trim().isEmpty()) return true;
        return false;
    }

    private void orderToTable(){

        rs = Database.getAllData("orders");

        try {
            while (rs.next()){
                orderList.add(new Order(rs.getString("item_order"), rs.getString("description_order"), rs.getInt("qty_order"),
                        rs.getDouble("total_order"), num++));
            }
            System.out.println("size = "+ orderList.size());

        } catch (SQLException se){
            se.printStackTrace();
            System.out.println("Cannot add order to table");
        }
        numberTable.setCellValueFactory(new PropertyValueFactory<>("number"));
        itemTable.setCellValueFactory(new PropertyValueFactory<>("item"));
        descripTable.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyTable.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        totalTable.setCellValueFactory(new PropertyValueFactory<>("total"));

        orderTable.setItems(null);
        orderTable.setItems(orderList);

    }

    @FXML
    private void handleQty(KeyEvent event){
        if(checkInteger() && isCheckForAdd()){
            String item = listOfItem.getValue().toString();
            checkQtyStock();
            ObservableList tot = Database.searchFromKey("items", "name_item", item, "price_item");
            double result = Double.parseDouble(tot.get(0).toString());
            int qty = Integer.parseInt(qtyAdd.getText());
            totalFromQty.setText(String.valueOf(qty*result));
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot sale item.");
            alert.setContentText("Invalid value or Incomplete information.");
            alert.showAndWait();
            ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
        }
    }

    @FXML
    private void handleAddButton(ActionEvent event){
        String item = "" ;
        String description = "";
        int qty = 0;
        double totalItem = 0;
        if(checkInteger() && isCheckForAdd()){
            item = listOfItem.getValue().toString();
            description = descriptionShow.getText();
            qty = Integer.parseInt(qtyAdd.getText());
            totalItem = Double.parseDouble(totalFromQty.getText());
            boolean check = false;
            List<String> itemCheck = itemsInOrder();
            for(String s: itemCheck){
                System.out.println("item check = " + s);
                if(s.equals(item)) {
                    check = true;
                    System.out.println("checked true find item = " + s);
                }
            }
            if(check){
                EditValue.editingQtyOfOrders(item, qty);
                EditValue.editingTotalOfOrders(item, totalItem);

            }else {
                Database.insertData("orders",  item, description, qty, (totalItem*qty));
            }

            ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);

        }

    }

    private List<String> itemsInOrder(){
        List<String> columnData = new ArrayList<>();
        for (Order item : orderTable.getItems()) {
            columnData.add(itemTable.getCellObservableValue(item).getValue());
        }

        return columnData;
    }


    @FXML
    private void handleDeleteButton(ActionEvent event){
        try {

            ObservableList<Order> orderSelected, allOrder;
            allOrder = orderTable.getItems();
            orderSelected = orderTable.getSelectionModel().getSelectedItems();
            Database.deleteData("orders", "item_order", orderSelected.get(0).getItem());
            orderSelected.forEach(allOrder::remove);
        }catch (NullPointerException ex){
            System.out.println("No select data from table.");
        }
    }

    @FXML
    private void handleSubmitButton(ActionEvent event){
        amount.setText(String.valueOf(Order.getAmount()));
        vat.setText(String.valueOf(Order.sumVat()));
        total.setText(String.valueOf(Order.allTotal()));
        qtyAdd.setEditable(false);

        status.getItems().addAll("paid", "unpaid");
        status.getSelectionModel().select(0);
    }

    @FXML
    private void handleSaveOrder(ActionEvent event){
        if(isCheckAll()) {
            String company = companyAdd.getText();
            LocalDate date = LocalDate.now();
            String dateOrder = date.toString();
            int receiptId = EditValue.getReceiptId();
            EditValue.addDetailItems(receiptId);
            Database.insertData("sales", dateOrder, receiptId, company,  EditValue.getQtySales(), Double.parseDouble(total.getText()), statusAdd);
            if(statusAdd.equals("paid")){
                EditValue.inventoryUpdate(orderList);
            }
            Database.deleteAllData("orders");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Added Order");
            alert.setHeaderText(null);
            alert.setContentText("Adding order, Successful!");

            alert.showAndWait();
            backButton.fire();
        }

    }

    @FXML
    private void handleCancelButton(ActionEvent event){
        qtyAdd.setEditable(true);
        amount.setText("");
        vat.setText("");
        total.setText("");
    }

    private void checkQtyStock(){
        ObservableList qtyStockList = Database.searchFromKey("items", "name_item", get, "quantity");
        int qtyStock = Integer.parseInt(qtyStockList.get(0).toString());
        int qtyCheck = Integer.parseInt(qtyAdd.getText());
        if((qtyStock - qtyCheck) <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot sale item.");
            alert.setContentText("Out of stock.");
            alert.showAndWait();
            ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
        }

    }


}
