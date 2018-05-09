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
import program.Database;
import program.Report;
import program.SaleDetail;
import program.Sales;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class SaleDetailController {
    @FXML
    AnchorPane pane;

    @FXML
    private Label receiptId;

    @FXML
    private Label companyName, status, quantity, amount, vat, total, date;

    @FXML
    private TableView<SaleDetail> detailTable;

    @FXML
    private TableColumn<SaleDetail, Integer> numberTable;

    @FXML
    private TableColumn<SaleDetail, String> itemNameTable;

    @FXML
    private TableColumn<SaleDetail, Integer> qtyTable;

    @FXML
    private TableColumn<SaleDetail, String> descriptionTable;

    @FXML
    private TableColumn<SaleDetail, Double> totalTable;

    private DatabaseManager db;
    private SaleDetailDao salesDetail = null;
    private SalesDao sales = null;

    private String companySale = "";
    private String dateSale = "";
    private int qtySale = 0;
    private double totalSale = 0;
    private String statusSale = "";
    private int receipt;
    private ObservableList<SaleDetail> observableList = FXCollections.observableArrayList();
    private int num = 1;
    private double amountSale = 0;

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

    @FXML
    private void handleSaveToPDF(){
        Report.salesDetailToPDF(salesDetail, receipt, companySale, statusSale, amountSale, Double.parseDouble(vat.getText()));
    }

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
