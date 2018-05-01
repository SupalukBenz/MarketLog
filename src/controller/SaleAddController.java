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
import java.util.ArrayList;
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

    private ResultSet rs = null;

    private String get;

    private int num = 1;

    @FXML
    public void initialize() {
        Database.deleteAllData("orders");
        addListToTable();
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
        if(!total.getText().trim().isEmpty() && !qtyAdd.getText().trim().isEmpty() && !totalFromQty.getText().trim().isEmpty()
                && !amount.getText().trim().isEmpty() && !vat.getText().trim().isEmpty() && !companyAdd.getText().trim().isEmpty()) return true;
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
            ObservableList tot = Database.searchFromKey("items", "name_item", item, "price_item");
            double result = Double.parseDouble(tot.get(0).toString());
            int qty = Integer.parseInt(qtyAdd.getText());
            totalFromQty.setText(String.valueOf(qty*result));
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

//            ObservableList<Order> allOrder = FXCollections.observableArrayList();
//            ObservableList<Order> orderSelected = FXCollections.observableArrayList();
//            allOrder = orderTable.getItems();
//            for(Order order: orderTable.getItems()){
//                if(order.getItem().equals(item)){
//                    orderSelected.add(order);
//                    num -= 1;
//                }
//            }
//            orderSelected.forEach(allOrder::remove);
            orderToTable();
            System.out.println(orderTable.getItems().size());
            qtyAdd.clear();
            totalFromQty.clear();
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
        listOfItem.setEditable(false);

        List<String> statusAdd = new ArrayList<>();
        statusAdd.add("paid");
        statusAdd.add("unpaid");
        status.getItems().addAll(statusAdd);
        status.getSelectionModel().select(0);

    }




}
