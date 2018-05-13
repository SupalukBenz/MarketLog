package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.ItemsDao;
import orm.SaleDetailDao;
import orm.SalesDao;
import program.*;

import java.sql.SQLException;
import java.util.List;

/**
 * SaleUpdateController is class for update status of sales.
 *
 * @author Supaluk Jaroensuk
 */
public class SaleUpdateController {

    /**
     * AnchorPane of UI.
     */
    @FXML
    AnchorPane pane;

    /**
     * Textfield for insert data that user want to search.
     */
    @FXML
    private TextField search;

    /**
     * TableView for showing list of unpaid orders.
     */
    @FXML
    private TableView<Sales> tableSaleStatus;

    /**
     * TableColumn for showing date of unpaid orders.
     */
    @FXML
    private TableColumn<Sales, String> date;

    /**
     * TableColumn for showing receipt id of unpaid orders.
     */
    @FXML
    private TableColumn<Sales, Integer> receiptId;

    /**
     * TableColumn for showing company of unpaid orders.
     */
    @FXML
    private TableColumn<Sales, String> company;

    /**
     * TableColumn for showing quantity of unpaid orders.
     */
    @FXML
    private TableColumn<Sales, Integer> qty;

    /**
     * TableColumn for showing total of unpaid orders.
     */
    @FXML
    private TableColumn<Sales, Double> total;

    /**
     * TableColumn for showing status of orders.
     */
    @FXML
    private TableColumn<Sales, String> status;

    /**
     * ObservableList of Sales for adding data to table.
     */
    private ObservableList<Sales> observableList = FXCollections.observableArrayList();

    /**
     * ObservableList of Sales that user selected.
     */
    private ObservableList<Sales> salesList = FXCollections.observableArrayList();

    /**
     * DatabaseManager class
     */
    private DatabaseManager db;

    /**
     * SalesDao access object for handle all database operation.
     */
    private SalesDao salesDao = null;

    /**
     * SaleDetailDao access object for handle all database operation.
     */
    private SaleDetailDao saleDetailDao = null;

    /**
     * ItemsDao access object for handle all database operation.
     */
    private ItemsDao itemsDao = null;

    /**
     * Initialize DatabaseManager for create SaleDetailDao, ItemsDao, SalesDao and show data of unpaid order to table.
     */
    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        salesDao = db.getSalesDao();
        saleDetailDao = db.getSaleDetailDao();
        itemsDao = db.getItemDao();
        readDataToTable();
    }

    /**
     * Update status to paid.
     */
    @FXML
    private void handleUpdate(){
        if(tableSaleStatus.getSelectionModel().getSelectedItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please, choose order list in table.");
            alert.showAndWait();
        }else {
            salesList = tableSaleStatus.getSelectionModel().getSelectedItems();
            changeStatus();
        }

    }

    /**
     * Update quantity stock of items.
     * @param id is id of sales that was updated.
     */
    private void listOfItem(int id){
        List<SaleDetail> getIdDetail = saleDetailDao.searchByColumnName("id_detail", id);
        for(SaleDetail saleDetail : getIdDetail){
            saleDetailDao.updateQuantityItem(itemsDao, saleDetail.getItem_detail(), saleDetail.getQty_detail());
        }

    }

    /**
     * Change status of sales in database.
     */
    private void changeStatus(){
        int receiptId = salesList.get(0).getReceiptId();
        System.out.println("receipt id : " + receiptId);
        listOfItem(receiptId);
        Sales sale = salesDao.getUpdateUnpaidFromId(receiptId);
        try {
            salesDao.update(new Sales(sale.getDate(), sale.getReceiptId(), sale.getCompany(), sale.getQuantity(), sale.getTotal(), "paid"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Update status");
        alert.setHeaderText("Order: " + receiptId + "\n" + salesList.get(0).getCompany());
        alert.setContentText("Paid, Successful!");

        alert.showAndWait();

        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }

    /**
     * Read data from database to table.
     */
    private void readDataToTable(){
        List<Sales> unpaidList = salesDao.searchByColumnName("status_sale", "unpaid");
        for(Sales sales: unpaidList){
            observableList.add(sales);
        }
        receiptId.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableSaleStatus.setItems(null);
        tableSaleStatus.setItems(observableList);

    }

    /**
     * Filter searching from value that user input.
     * @param key is action on textfield.
     */
    @FXML
    private void filter(KeyEvent key){
        Sales.initFilter(search, tableSaleStatus, observableList);
    }

    /**
     * Handle backing to sales page.
     */
    @FXML
    private void handleBackButton(){
        ChangePage.changeUI("UI/SaleUI.fxml", pane);
    }
}
