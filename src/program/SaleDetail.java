package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sale_id_details")
public class SaleDetail {

    @DatabaseField(id = true)
    private int number_detail;
    @DatabaseField
    private int id_detail;
    @DatabaseField
    private String item_detail;
    @DatabaseField
    private String description_detail;
    @DatabaseField
    private int qty_detail;
    @DatabaseField
    private double total_detail;

    public SaleDetail(){

    }

    public SaleDetail(int number_detail, int id_detail, String item_detail, String description_detail, int qty_detail, double total_detail) {
        this.number_detail = number_detail;
        this.id_detail = id_detail;
        this.item_detail = item_detail;
        this.description_detail = description_detail;
        this.qty_detail = qty_detail;
        this.total_detail = total_detail;
    }

    public int getNumber_detail() {
        return number_detail;
    }

    public int getId_detail() {
        return id_detail;
    }

    public String getItem_detail() {
        return item_detail;
    }

    public String getDescription_detail() {
        return description_detail;
    }

    public int getQty_detail() {
        return qty_detail;
    }

    public double getTotal_detail() {
        return total_detail;
    }
}
