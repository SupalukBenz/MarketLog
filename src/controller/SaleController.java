package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import orm.DatabaseManager;
import orm.SalesDao;
import program.*;

import java.io.IOException;

/**
 * SaleController is class for controller manging sales and showing total sales.
 *
 * @author Supaluk Jaroensuk
 */
public class SaleController{

    /**
     * AnchorPane of UI.
     */
    @FXML
    private AnchorPane pane;

    /**
     * TableView for showing total sales.
     */
    @FXML
    private TableView<Sales> tableSale;

    /**
     * TableColumn for showing date of sales.
     */
    @FXML
    private TableColumn<Sales, String> date;

    /**
     * TableColumn for showing receipt id of sales.
     */
    @FXML
    private TableColumn<Sales, Integer> receiptId;

    /**
     * TableColumn for showing company of sales.
     */
    @FXML
    private TableColumn<Sales, String> company;

    /**
     * TableColumn for showing quantity of sales.
     */
    @FXML
    private TableColumn<Sales, Integer> qty;

    /**
     * TableColumn for showing total of sales.
     */
    @FXML
    private TableColumn<Sales, Double> total;

    /**
     * TableColumn for showing status of sales.
     */
    @FXML
    private TableColumn<Sales, String> status;

    /**
     * Textfield insert details of sales for searching.
     */
    @FXML
    private TextField search;

    /**
     * id of sales, as a static for using in sale detail controller.
     */
    private static int idDetail;

    /**
     * ObservableList of Sales.
     */
    private ObservableList<Sales> observableList = FXCollections.observableArrayList();

    /**
     * DatabaseManager class.
     */
    private DatabaseManager db;

    /**
     * SalesDao access object for handle all database operation.
     */
    private SalesDao salesDao = null;

    /**
     * Initialize DatabaseManager for create SalesDao, and show data of sales in table.
     */
    @FXML
    public void initialize() {
        db = DatabaseManager.getInstance();
        salesDao = db.getSalesDao();
        readDataToTable();
    }

    /**
     * Read details of sales to table.
     */
    public void readDataToTable(){

        for(Sales sales: salesDao){
            observableList.add(sales);
        }

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        receiptId.setCellValueFactory(new PropertyValueFactory<>("receiptId"));
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableSale.setItems(null);
        tableSale.setItems(observableList);

    }

    /**
     * Handle changing page for add order.
     * @param event is action on button.
     */
    @FXML
    private void handleAddOrder(ActionEvent event){
        ChangePage.changeUI("UI/SaleOrderUI.fxml", pane);
    }

    /**
     * Handle filter searching sales by input details.
     * @param key is action on textfield.
     */
    @FXML
    private void filter(KeyEvent key){
        Sales.initFilter(search, tableSale, observableList);
    }

    /**
     * Handle updating status of sale order.
     */
    @FXML
    private void handleUpdateStatus(){
        ChangePage.changeUI("UI/SaleStatusUI.fxml", pane);
    }

    /**
     * Handle showing detail of sales by selecting on table.
     */
    @FXML
    private void handleShowDetail(){
        if(checkClickTable(tableSale)) {
            ObservableList<Sales> salesList = tableSale.getSelectionModel().getSelectedItems();
            idDetail = salesList.get(0).getReceiptId();

            Parent root;
            try {
                root = (Parent) FXMLLoader.load(getClass().getResource("/UI/ShowDetailUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Item Detail");
                stage.setScene(new Scene(root, 600, 620));
                stage.getIcons().add(new Image("/UI/photos/MarketLogIcon.png"));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handle saving list of sales to pdf file.
     */
    @FXML
    public void handleSavePDFSales(){
        Report.salesToPdf(salesDao);
    }

    /**
     * Get id of sales.
     * @return id of sales.
     */
    public static int getIdDetail(){
        return idDetail;
    }

    /**
     * Check that user select sale on table or not.
     * @param table is TableView.
     * @return true if user selected sale on table, and false if user didn't select.
     */
    private boolean checkClickTable(TableView<?> table){
        if(table.getSelectionModel().getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please, choose sale list in table.");
            alert.showAndWait();
            return false;
        }
        return true;
    }




}
