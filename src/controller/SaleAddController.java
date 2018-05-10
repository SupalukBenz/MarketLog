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
import orm.*;
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

    private String get, statusAdd;

    private int qtySales = 0;

    private int number = 1;

    private DatabaseManager db;
    private SalesDao salesDao = null;
    private SaleDetailDao saleDetailDao = null;
    private OrderDao orderDao = null;
    private ItemsDao itemsDao = null;

    @FXML
    public void initialize() {
        db = DatabaseManager.getInstance();
        salesDao = db.getSalesDao();
        saleDetailDao = db.getSaleDetailDao();
        itemsDao = db.getItemDao();
        orderDao = db.getOrderDao();
        addListToTable();
        orderToTable();
    }

    private void addListToTable(){
        List<String> items = new ArrayList<>();
        for(Item item: itemsDao){
            items.add(item.getName_item());
        }
        listOfItem.getItems().addAll(items);
        listOfItem.getSelectionModel().select(0);
        handleSelectItem();
    }

    @FXML
    private void handleSelectItem(){
        get = listOfItem.getValue().toString();
        descriptionShow.clear();
        totalFromQty.clear();
        qtyAdd.clear();
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
        Item item = itemsDao.getItemFromKey("name_item", value);
        descriptionShow.setText(item.getDescription_item());
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

        for(Order order: orderDao){
            orderList.add(order);
            order.setNumber(number++);
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
//            ObservableList tot = Database.searchFromKey("items", "name_item", item, "price_item");
            Item itemFromKey = itemsDao.getItemFromKey("name_item", item);
            double result = itemFromKey.getTotal_item();
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
                System.out.println("check");
                orderDao.editedOrder(orderDao, item, qty, totalItem);
            }else {
                System.out.println("insert");
                Order addOrder = new Order(item, description, qty, totalItem);
                try {
                    orderDao.create(addOrder);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
//                Database.insertData("orders",  item, description, qty, totalItem);
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
            try {
                orderDao.deleteById( orderSelected.get(0).getItem());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            orderSelected.forEach(allOrder::remove);
            ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
        }catch (NullPointerException ex){
            System.out.println("No select data from table.");
        }
    }

    @FXML
    private void handleSubmitButton(ActionEvent event){
        double amountOfOrder = orderDao.getAmountOfOrder(orderDao);
        double vatOfAmount = (amountOfOrder*10)/100;
        double totalOrder = Math.round(amountOfOrder + vatOfAmount);

        amount.setText(String.valueOf(amountOfOrder));
        vat.setText(String.valueOf(vatOfAmount));
        total.setText(String.valueOf(totalOrder));
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
            int receiptId = getReceiptId();
            addDetailItems(receiptId);
            Sales addSale = new Sales(dateOrder, receiptId, company, qtySales, Double.parseDouble(total.getText()), statusAdd);
            System.out.println("addSale id = " + receiptId);
            try {
                salesDao.create(addSale);
                System.out.println("saleDao ? : " + salesDao.searchByColumnName("receipt_id", "10005"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(statusAdd.equals("paid")){
                for(Order order: orderDao) {
                    System.out.println("order name = " + order.getItem());
                    saleDetailDao.updateQuantityItem(itemsDao, order.getItem(), order.getQuantity());
                }
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


    private void addDetailItems(int receipt) {
        for(Order order: orderDao){
            SaleDetail saleDetail = new SaleDetail(getNumberDetail(), receipt, order.getItem(), order.getDescription(), order.getQuantity(), order.getTotal());
            try {
                saleDetailDao.create(saleDetail);
                qtySales+=order.getQuantity();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private int getNumberDetail(){
        ObservableList id = Database.selectItem("sale_id_details", "number_detail");
        if(id == null || id.isEmpty()){
            return 1;
        }else{
            int currentId = Integer.parseInt((String) id.get(id.size() - 1));
            return currentId + 1;
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event){
        qtyAdd.setEditable(true);
        amount.clear();
        vat.clear();
        total.clear();
    }

    private void checkQtyStock(){
        Item itemCheck = itemsDao.getItemFromKey("name_item", get);
        int qtyStock = itemCheck.getQuantity_item();
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

    private int getReceiptId(){
        ObservableList id = Database.selectItem("sales", "receipt_id");
        if(id == null || id.isEmpty()){
            return 10001;
        }else{
            int currentId = Integer.parseInt((String) id.get(id.size() - 1));
            return currentId + 1;
        }
    }


}
