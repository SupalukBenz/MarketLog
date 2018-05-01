package program;

import javafx.beans.property.*;

/**
 *
 *
 * @author Supaluk
 */

public class Items {

//    private final IntegerProperty num;
    private final StringProperty name;
    private final StringProperty description;
    private final DoubleProperty price;
    private final IntegerProperty qty;
    private final IntegerProperty test;


    public Items(String name, String description, double price, int qty, int test) {
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleDoubleProperty(price);
        this.qty = new SimpleIntegerProperty(qty);
        this.test = new SimpleIntegerProperty(test);
    }

    public String getName() {
        return name.get();
    }

    public String getDescription() {
        return description.get();
    }


    public double getPrice() {
        return price.get();
    }


    public int getQty() {
        return qty.get();
    }

    public int getTest(){ return test.get(); }


}
