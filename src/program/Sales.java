package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Sales is class that persist to SQL database.
 *
 * @author Supaluk
 */

@DatabaseTable(tableName = "sales")
public class Sales {

    /**
     * Column of sales table that store date sale.
     */
    @DatabaseField
    private String date_sale;

    /**
     * Column of sales table that store receipt id sale.
     */
    @DatabaseField(id = true)
    private int receipt_id;

    /**
     * Column of sales table that store company sale.
     */
    @DatabaseField
    private String company;

    /**
     * Column of sales table that store quantity sale.
     */
    @DatabaseField
    private int qty_sale;

    /**
     * Column of sales table that store total sale.
     */
    @DatabaseField
    private double total_sale;

    /**
     * Column of sales table that status date sale.
     */
    @DatabaseField
    private String status_sale;

    /**
     * Initialize details of sales.
     * @param date is date of sales.
     * @param receiptId is receipt id of sale.
     * @param company is company of sale.
     * @param quantity is company of sale.
     * @param total is total of sale.
     * @param status is status of sale.
     */
    public Sales(String date, int receiptId, String company, int quantity, double total, String status) {
        this.date_sale = date;
        this.receipt_id = receiptId;
        this.company = company;
        this.qty_sale = quantity;
        this.total_sale = total;
        this.status_sale = status;
    }

    /**
     * no-arg constructor
     */
    public Sales(){

    }

    /**
     * Get date of sale.
     * @return date of sale.
     */
    public String getDate(){
        return date_sale;
    }

    /**
     * Get receipt id of sale.
     * @return receipt of sale.
     */
    public int getReceiptId() {
        return receipt_id;
    }

    /**
     * Get company of sale.
     * @return company of sale.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Get quantity of sale.
     * @return quantity of sale.
     */
    public int getQuantity() {
        return qty_sale;
    }

    /**
     * Get total of sale.
     * @return total of sale.
     */
    public double getTotal() {
        return total_sale;
    }

    /**
     * Get status of sale.
     * @return status of sale.
     */
    public String getStatus(){
        return status_sale;
    }

    /**
     * Initialize filter for searching sales data in SaleController, and SaleDetailController.
     * @param search
     * @param tableSale
     * @param observableList
     */
    public static void initFilter(TextField search, TableView<Sales> tableSale, ObservableList<Sales> observableList) {
        search.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if(search.textProperty().get().isEmpty()){
                    tableSale.setItems(observableList);
                    return;
                }
                ObservableList<Sales> tableData = FXCollections.observableArrayList();
                ObservableList<TableColumn<Sales, ?>> cols = tableSale.getColumns();

                for(int i=0; i<observableList.size(); i++){

                    for(int k=0; k<cols.size(); k++){
                        TableColumn col = cols.get(k);
                        String data = col.getCellData(observableList.get(i)).toString().toLowerCase();
                        if(data.contains(search.textProperty().get().toLowerCase())){
                            tableData.add(observableList.get(i));
                            break;
                        }
                    }
                }
                tableSale.setItems(tableData);
            }
        });
    }
}
