package controller;

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

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * SaleAddController is class for adding sales.
 */
public class SaleAddController {

    /**
     * AnchorPane of UI
     */
    @FXML
    AnchorPane pane;

    /**
     * TextField for inserting details of sales.
     */
    @FXML
    private TextField qtyAdd, total, totalFromQty, amount, vat, companyAdd, descriptionShow;

    /**
     * TableView for showing order was added.
     */
    @FXML
    private TableView<Order> orderTable;

    /**
     * TableColumn for showing detail of name item.
     */
    @FXML
    private TableColumn<Order, String> itemTable;

    /**
     * TableColumn for showing detail of quantity item.
     */
    @FXML
    private TableColumn<Order, Integer> qtyTable;

    /**
     * TableColumn for showing detail of description item.
     */
    @FXML
    private TableColumn<Order, String> descripTable;

    /**
     * TableColumn for showing detail of total item.
     */
    @FXML
    private TableColumn<Order, Double> totalTable;

    /**
     * TableColumn for showing detail of number item.
     */
    @FXML
    private TableColumn<Order, Integer> numberTable;

    /**
     * ObservableList of Order.
     */
    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    /**
     * ComboBox consist of name's item.
     */
    @FXML
    private ComboBox listOfItem;

    /**
     * ComboBox for selecting status.
     */
    @FXML
    private ComboBox status;

    /**
     * Button for back to sales page.
     */
    @FXML
    private Button backButton;

    /**
     * Detail from ComboBox, as a String.
     */
    private String get, statusAdd;

    /**
     * Value of quantity total items.
     */
    private int qtySales = 0;

    /**
     * Number for counting list of order.
     */
    private int number = 1;

    /**
     * DatabaseManager class
     */
    private DatabaseManager db;

    /**
     * SaleDao access object for handle all database operation.
     */
    private SalesDao salesDao = null;

    /**
     * SalesDetailDao access object for handle all database operation.
     */
    private SaleDetailDao saleDetailDao = null;

    /**
     * OrderDao access object for handle all database operation.
     */
    private OrderDao orderDao = null;

    /**
     * ItemsDao access object for handle all database operation.
     */
    private ItemsDao itemsDao = null;

    /**
     * Initialize DatabaseManager for create SalesDao, SalesDetailDao, ItemsDao, and OrderDao and show data table.
     */
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

    /**
     * Adding list of name item in ComboBox.
     */
    private void addListToTable(){
        List<String> items = new ArrayList<>();
        for(Item item: itemsDao){
            items.add(item.getName_item());
        }
        listOfItem.getItems().addAll(items);
        listOfItem.getSelectionModel().select(0);
        handleSelectItem();
    }

    /**
     * Handle selecting item on ComboBox and show description of that item.
     */
    @FXML
    private void handleSelectItem(){
        get = listOfItem.getValue().toString();
        descriptionShow.clear();
        totalFromQty.clear();
        qtyAdd.clear();
        showDescription(get);
    }

    /**
     * Handle selecting status that paid or unpaid.
     */
    @FXML
    private void handleSelectStatus(){
        statusAdd = status.getValue().toString();
        System.out.println("status : " + statusAdd);
    }

    /**
     * Handle backing to sales page.
     * @param event
     */
    @FXML
    private void handleBackToSale(ActionEvent event){
        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }

    /**
     * Show description of item that was selected.
     * @param value
     */
    private void showDescription(String value){
        Item item = itemsDao.getItemFromKey("name_item", value);
        descriptionShow.setText(item.getDescription_item());
    }

    /**
     * Check that value of quantity is int or not.
     * @return true if quantity is integer, false if quantity is not integer.
     */
    private boolean checkInteger(){
        if(qtyAdd.getText().trim().matches("^[\\d]+$")) return true;
        return false;
    }

    /**
     * Check that all of details was filled.
     * @return true if details is complete, false if detail is incomplete.
     */
    private boolean isCheckAll(){
        if(!total.getText().trim().isEmpty() && !amount.getText().trim().isEmpty() && !vat.getText().trim().isEmpty() && !companyAdd.getText().trim().isEmpty()) return true;
        return false;
    }

    /**
     * Check that quantity of item was inserted.
     * @return true if quantity was inserted, false if quantity was not inserted.
     */
    private boolean isCheckForAdd(){
        if(!qtyAdd.getText().trim().isEmpty()) return true;
        return false;
    }

    /**
     * Insert details of order to table.
     */
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

    /**
     * Handle textfield quantity is not integer.
     * @param event is action on button.
     */
    @FXML
    private void handleQty(KeyEvent event){
        if(checkInteger() && isCheckForAdd()){
            String item = listOfItem.getValue().toString();
            checkQtyStock();
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

    /**
     * Handle adding sales order.
     * @param event is action of button.
     */
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
                if(s.equals(item)) {
                    check = true;
                }
            }

            if(check){
                orderDao.editedOrder(orderDao, item, qty, totalItem);
            }else {
                Order addOrder = new Order(item, description, qty, totalItem);
                try {
                    orderDao.create(addOrder);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);

        }

    }

    /**
     * Get all of data in table.
     * @return list of details order on table.
     */
    private List<String> itemsInOrder(){
        List<String> columnData = new ArrayList<>();
        for (Order item : orderTable.getItems()) {
            columnData.add(itemTable.getCellObservableValue(item).getValue());
        }

        return columnData;
    }

    /**
     * Handle deleting orders on table.
     * @param event is action on button.
     */
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

    /**
     * Handle submitting sales order for get amount.
     * @param event is action on button.
     */
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

    /**
     * Handle saving details order of sales to database.
     * @param event is action on button.
     */
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

    /**
     * Add details of items into database.
     * @param receipt is receipt id of sales order.
     */
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

    /**
     * Get current number of sales details database.
     * @return 1 if sales details database has not data, or return a next id if sales details database has data.
     */
    private int getNumberDetail(){
        ObservableList id = Database.selectItem("sale_id_details", "number_detail");
        if(id == null || id.isEmpty()){
            return 1;
        }else{
            int currentId = Integer.parseInt((String) id.get(id.size() - 1));
            return currentId + 1;
        }
    }

    /**
     * Handle canceling when user want to add more items.
     * @param event
     */
    @FXML
    private void handleCancelButton(ActionEvent event){
        qtyAdd.setEditable(true);
        amount.clear();
        vat.clear();
        total.clear();
    }

    /**
     * Check that quantity is greater than or equal to stock of that item.
     */
    private void checkQtyStock(){
        Item itemCheck = itemsDao.getItemFromKey("name_item", get);
        int qtyStock = itemCheck.getQuantity_item();
        int qtyCheck = Integer.parseInt(qtyAdd.getText());
        if((qtyStock - qtyCheck) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Cannot sale item.");
            alert.setContentText("Out of stock.");
            alert.showAndWait();
            ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
        }

    }

    /**
     * Get receipt id of order sales.
     * @return 10001 if sales database has not data, or return a next receipt id.
     */
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
