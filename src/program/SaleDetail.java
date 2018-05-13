package program;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * SaleDetail is class that persist to SQL database.
 *
 * @author Supaluk
 */
@DatabaseTable(tableName = "sale_id_details")
public class SaleDetail {

    /**
     * Column of sale details table that store number sale details.
     */
    @DatabaseField(id = true)
    private int number_detail;

    /**
     * Column of sale details table that store id sale details.
     */
    @DatabaseField
    private int id_detail;

    /**
     * Column of sale details table that store name item.
     */
    @DatabaseField
    private String item_detail;

    /**
     * Column of sale details table that store description item.
     */
    @DatabaseField
    private String description_detail;

    /**
     * Column of sale details table that store quantity item.
     */
    @DatabaseField
    private int qty_detail;

    /**
     * Column of sale details table that store total item.
     */
    @DatabaseField
    private double total_detail;

    /**
     * no-arg constructor
     */
    public SaleDetail(){

    }

    /**
     * Initialize details of sale order.
     * @param number_detail is number of sale.
     * @param id_detail is id of sale.
     * @param item_detail is item of sale.
     * @param description_detail is description of sale.
     * @param qty_detail is quantity of sale.
     * @param total_detail is total of sale.
     */
    public SaleDetail(int number_detail, int id_detail, String item_detail, String description_detail, int qty_detail, double total_detail) {
        this.number_detail = number_detail;
        this.id_detail = id_detail;
        this.item_detail = item_detail;
        this.description_detail = description_detail;
        this.qty_detail = qty_detail;
        this.total_detail = total_detail;
    }

    /**
     * Get number of sale detail.
     * @return number of sale detail.
     */
    public int getNumber_detail() {
        return number_detail;
    }

    /**
     * Get id of sale detail.
     * @return id of sale detail.
     */
    public int getId_detail() {
        return id_detail;
    }

    /**
     * Get item of sale detail.
     * @return item of sale detail.
     */
    public String getItem_detail() {
        return item_detail;
    }

    /**
     * Get description of sale detail.
     * @return id of description detail.
     */
    public String getDescription_detail() {
        return description_detail;
    }

    /**
     * Get quantity of sale detail.
     * @return quantity of sale detail.
     */
    public int getQty_detail() {
        return qty_detail;
    }

    /**
     * Get total of sale detail.
     * @return total of sale detail.
     */
    public double getTotal_detail() {
        return total_detail;
    }

    /**
     * Get number of sale detail.
     * @return number of sale detail.
     */
    public void setNumber_detail(int number_detail) {
        this.number_detail = number_detail;
    }
}
