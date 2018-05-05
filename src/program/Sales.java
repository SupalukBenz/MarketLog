package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 *
 * @author Supaluk
 */

@DatabaseTable(tableName = "sales")
public class Sales {

    @DatabaseField
    private String date_sale;
    @DatabaseField(id = true)
    private int receipt_id;
    @DatabaseField
    private String company;
    @DatabaseField
    private int qty_sale;
    @DatabaseField
    private double total_sale;
    @DatabaseField
    private String status_sale;

    public Sales(String date, int receiptId, String company, int quantity, double total, String status) {
        this.date_sale = date;
        this.receipt_id = receiptId;
        this.company = company;
        this.qty_sale = quantity;
        this.total_sale = total;
        this.status_sale = status;
    }


    public String getDate(){
        return date_sale;
    }

    public int getReceiptId() {
        return receipt_id;
    }

    public String getCompany() {
        return company;
    }

    public int getQuantity() {
        return qty_sale;
    }

    public double getTotal() {
        return total_sale;
    }

    public String getStatus(){
        return status_sale;
    }

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
