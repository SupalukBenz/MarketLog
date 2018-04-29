package program;

import java.time.LocalDate;
import java.util.List;

/**
 *
 *
 * @author Supaluk
 */

public class Sales {

    private List<Items> items;
    private LocalDate date;
    private int receiptId;
    private String company;
    private int quantity;
    private boolean status;
    private double total;

    public Sales(List<Items> items, LocalDate date, int receiptId, String company, int quantity, boolean status, double total) {
        this.items = items;
        this.date = date;
        this.receiptId = receiptId;
        this.company = company;
        this.quantity = quantity;
        this.status = status;
        this.total = total;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
