package program;

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

public class Sales {

    private StringProperty date;
    private IntegerProperty receiptId;
    private StringProperty company;
    private IntegerProperty quantity;
    private DoubleProperty total;
    private StringProperty status;


    public Sales(String date, int receiptId, String company, int quantity, double total, String status) {
        this.date = new SimpleStringProperty(date);
        this.receiptId = new SimpleIntegerProperty(receiptId);
        this.company = new SimpleStringProperty(company);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.total = new SimpleDoubleProperty(total);
        this.status = new SimpleStringProperty(status);
    }


    public String getDate(){
        return date.get();
    }

    public int getReceiptId() {
        return receiptId.get();
    }

    public String getCompany() {
        return company.get();
    }

    public int getQuantity() {
        return quantity.get();
    }

    public double getTotal() {
        return total.get();
    }

    public String getStatus(){
        return status.get();
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
