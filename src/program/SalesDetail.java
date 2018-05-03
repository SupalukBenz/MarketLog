package program;

import javafx.beans.property.*;

/**
 *
 *
 * @author Supaluk
 */

public class SalesDetail {

        private StringProperty item;
        private IntegerProperty number;
        private IntegerProperty qty;
        private DoubleProperty total;
        private StringProperty description;

        public SalesDetail(String item, int num, int qty, double total, String description){
            this.item = new SimpleStringProperty(item);
            this.number = new SimpleIntegerProperty(num);
            this.qty = new SimpleIntegerProperty(qty);
            this.total = new SimpleDoubleProperty(total);
            this.description = new SimpleStringProperty(description);
        }

    public String getItem() {
        return item.get();
    }

    public int getNumber() {
        return number.get();
    }

    public int getQty() {
        return qty.get();
    }

    public double getTotal() {
        return total.get();
    }

    public String getDescription() {
        return description.get();
    }
}
