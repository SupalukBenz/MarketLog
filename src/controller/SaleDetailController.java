package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import orm.DatabaseManager;
import orm.SaleDetailDao;
import orm.SalesDao;
import program.Report;
import program.SaleDetail;
import program.Sales;

import java.util.List;

/**
 * SaleDetailController is class for showing details of sales.
 *
 * @author Supaluk Jaroensuk
 */
public class SaleDetailController {

    /**
     * AnchorPane of UI
     */
    @FXML
    AnchorPane pane;

    /**
     * Text label is details of sale.
     */
    @FXML
    private Label receiptId, companyName, status, quantity, amount, vat, total, date;

    /**
     * TableView for showing details of sales.
     */
    @FXML
    private TableView<SaleDetail> detailTable;

    /**
     * TableColumn for showing number of sale.
     */
    @FXML
    private TableColumn<SaleDetail, Integer> numberTable;

    /**
     * TableColumn for showing name of item.
     */
    @FXML
    private TableColumn<SaleDetail, String> itemNameTable;

    /**
     * TableColumn for showing quantity of sale.
     */
    @FXML
    private TableColumn<SaleDetail, Integer> qtyTable;

    /**
     * TableColumn for showing description of item.
     */
    @FXML
    private TableColumn<SaleDetail, String> descriptionTable;

    /**
     * TableColumn for showing total of sale.
     */
    @FXML
    private TableColumn<SaleDetail, Double> totalTable;

    /**
     * DatabaseManager class.
     */
    private DatabaseManager db;

    /**
     * SaleDetailDao access object for handle all database operation.
     */
    private SaleDetailDao salesDetail = null;

    /**
     * SalesDao access object for handle all database operation.
     */
    private SalesDao sales = null;

    /**
     * Detail of order, as a String.
     */
    private String companySale, dateSale, statusSale = "";

    /**
     * Total quantity of items.
     */
    private int qtySale = 0;

    /**
     * Detail of total sales, as a double.
     */
    private double totalSale, amountSale = 0;

    /**
     * Receipt id of sale order, as a int.
     */
    private int receipt;

    /**
     * ObservableList of SaleDetail
     */
    private ObservableList<SaleDetail> observableList = FXCollections.observableArrayList();

    /**
     * Initialize DatabaseManager for create SaleDetailDao and show details of sale.
     */
    @FXML
    private void initialize(){
        db = DatabaseManager.getInstance();
        salesDetail = db.getSaleDetailDao();
        sales = db.getSalesDao();

        receipt = SaleController.getIdDetail();
        receiptId.setText(String.valueOf(receipt));
        readDataToTable();
        setDetail();
    }

    /**
     * Handle saving details to pdf file.
     */
    @FXML
    private void handleSaveToPDF(){
        Report.salesDetailToPDF(salesDetail, receipt, companySale, statusSale, amountSale, Double.parseDouble(vat.getText()));
    }

    /**
     * Read data from database to table.
     */
    private void readDataToTable(){
        amountSale = 0;
        int number = 0;

        for(SaleDetail saleDetail: salesDetail){
            if(saleDetail.getId_detail() == receipt){
                number++;
                saleDetail.setNumber_detail(number);
                observableList.add(saleDetail);
                amountSale += saleDetail.getTotal_detail();
            }
        }

        numberTable.setCellValueFactory(new PropertyValueFactory<>("number_detail"));
        itemNameTable.setCellValueFactory(new PropertyValueFactory<>("item_detail"));
        descriptionTable.setCellValueFactory(new PropertyValueFactory<>("description_detail"));
        qtyTable.setCellValueFactory(new PropertyValueFactory<>("qty_detail"));
        totalTable.setCellValueFactory(new PropertyValueFactory<>("total_detail"));

        detailTable.setItems(null);
        detailTable.setItems(observableList);
    }

    /**
     * Set details to textfield.
     */
    private void setDetail(){

        List<Sales> salesList = sales.searchByReceiptId(receipt);
        Sales saleFromId = salesList.get(0);
        companySale = saleFromId.getCompany();
        companyName.setText(companySale);

        dateSale = saleFromId.getDate();
        date.setText(dateSale);

        qtySale = saleFromId.getQuantity();
        quantity.setText(String.valueOf(qtySale));

        totalSale = saleFromId.getTotal();
        total.setText(String.valueOf(totalSale));

        statusSale = saleFromId.getStatus();
        status.setText(statusSale);

        amount.setText(String.valueOf(amountSale));
        double vatSale = ((amountSale*10)/100);
        vat.setText(String.valueOf(vatSale));

        if(status.getText().equals("unpaid")){
            status.setTextFill(Color.RED);
        }

    }


}
