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
import program.Database;
import program.SalesDetail;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SaleDetailController {
    @FXML
    AnchorPane pane;

    @FXML
    private Label receiptId;

    @FXML
    private Label companyName, status, quantity, amount, vat, total, date;

    @FXML
    private TableView<SalesDetail> detailTable;

    @FXML
    private TableColumn<SalesDetail, Integer> numberTable;

    @FXML
    private TableColumn<SalesDetail, String> itemNameTable;

    @FXML
    private TableColumn<SalesDetail, Integer> qtyTable;

    @FXML
    private TableColumn<SalesDetail, String> descriptionTable;

    @FXML
    private TableColumn<SalesDetail, Double> totalTable;

    private int receipt;
    private ObservableList<SalesDetail> observableList = FXCollections.observableArrayList();
    private int num = 1;
    private double amountSale = 0;

    @FXML
    private void initialize(){
        receipt = SaleController.getIdDetail();
        receiptId.setText(String.valueOf(receipt));
        readDataToTable();
        setDetail();
    }

    private void readDataToTable(){
        ResultSet rs = Database.getAllData("sale_id_details");
        amountSale = 0;

        try {
            while (rs.next()){
                if(rs.getString("id_detail").equals(String.valueOf(receipt))) {
                    observableList.add(new SalesDetail(rs.getString("item_detail"), num++,
                            rs.getInt("qty_detail"), rs.getDouble("total_detail"), rs.getString("description_detail")));
                    amountSale += rs.getInt("total_detail");
                }
            }

        }catch (SQLException se){
            se.printStackTrace();
        }

        numberTable.setCellValueFactory(new PropertyValueFactory<>("number"));
        itemNameTable.setCellValueFactory(new PropertyValueFactory<>("item"));
        descriptionTable.setCellValueFactory(new PropertyValueFactory<>("description"));
        qtyTable.setCellValueFactory(new PropertyValueFactory<>("qty"));
        totalTable.setCellValueFactory(new PropertyValueFactory<>("total"));

        detailTable.setItems(null);
        detailTable.setItems(observableList);
    }

    private void setDetail(){

        ResultSet rs = Database.getAllData("sales");
        String companySale = "";
        String dateSale = "";
        int qtySale = 0;
        double totalSale = 0;
        String statusSale = "";
        try {
            while (rs.next()){
                if(rs.getInt("receipt_id") == receipt){
                    companySale = rs.getString("company");
                    dateSale = rs.getString("date_sale");
                    totalSale = rs.getDouble("total_sale");
                    qtySale = rs.getInt("qty_sale");
                    statusSale = rs.getString("status_sale");
                }

            }

        }catch (SQLException se){
            se.printStackTrace();
        }

        companyName.setText(companySale);
        date.setText(dateSale);
        quantity.setText(String.valueOf(qtySale));
        total.setText(String.valueOf(totalSale));
        status.setText(statusSale);
        amount.setText(String.valueOf(amountSale));
        double vatSale = ((amountSale*10)/100);
        vat.setText(String.valueOf(vatSale));

        if(statusSale.equals("unpaid")){
            status.setTextFill(Color.RED);
        }

    }


}
