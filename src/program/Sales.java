package program;

import javafx.beans.property.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

/**
 *
 *
 * @author Supaluk
 */

public class Sales {

    private Date date;
    private IntegerProperty receiptId;
    private StringProperty company;
    private IntegerProperty quantity;
    private boolean status;
    private DoubleProperty total;
    private StringProperty dateToString;


    public Sales(Date date, int receiptId, String company, int quantity, double total, boolean status) {
        this.date = date;

        this.receiptId = new SimpleIntegerProperty(receiptId);
        this.company = new SimpleStringProperty(company);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.status = status;
        this.total = new SimpleDoubleProperty(total);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTotal() {
        return total.get();
    }


}
