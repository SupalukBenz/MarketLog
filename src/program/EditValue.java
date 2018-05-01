package program;

import javafx.collections.ObservableList;

public class EditValue {
    public static boolean editingQtyOfItems(String item, int orderQty){
        ObservableList qtyItem = Database.searchFromKey("items", "name_item", item, "quantity");
        int currentQty = Integer.parseInt(qtyItem.get(0).toString());

        int result = currentQty - orderQty;
        if ( result <= 0 ){
            return false;
        } else{
            Database.updateData("items", "quantity", result, "name_item", item);
            return true;
        }
    }

    public static void editingQtyOfOrders(String item, int addQty){
        ObservableList qtyItem = Database.searchFromKey("orders", "item_order", item, "qty_order");
        int currentQty = Integer.parseInt(qtyItem.get(0).toString());

        int result = currentQty + addQty;
        Database.updateData("orders", "qty_order", result, "item_order", item);
    }

    public static void editingTotalOfOrders(String item, double addTotal){
        ObservableList totalItem = Database.searchFromKey("orders", "item_order", item, "total_order");
        double currentQty = Double.parseDouble(totalItem.get(0).toString());

        double result = currentQty + addTotal;
        Database.updateData("orders", "total_order", result, "item_order", item);

    }

}
