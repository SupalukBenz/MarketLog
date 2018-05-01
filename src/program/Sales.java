package program;

import javafx.beans.property.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

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


}
