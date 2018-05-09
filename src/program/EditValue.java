package program;

import javafx.collections.ObservableList;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class EditValue {
//    public static void editingQtyOfItems(String item, int orderQty){
//        ObservableList qtyItem = Database.searchFromKey("items", "name_item", item, "quantity");
//        int currentQty = Integer.parseInt(qtyItem.get(0).toString());
//
//        int result = currentQty - orderQty;
//        Database.updateData("items", "quantity", result, "name_item", item);
//    }

//    public static void inventoryUpdate(ObservableList<Order> orderList){
//        List<String> itemName = new ArrayList<>();
//        List<Integer> qtyOrder = new ArrayList<>();
//        for(int i = 0; i <= orderList.size() -1; i++){
//            itemName.add(orderList.get(i).getItem());
//            qtyOrder.add(orderList.get(i).getQuantity());
//        }
//        for(int j = 0; j <= itemName.size()-1; j++){
//            editingQtyOfItems(itemName.get(j), qtyOrder.get(j));
//        }
//    }


    public static void updateStock(String item, int quantity){
        ObservableList qtyItem = Database.searchFromKey("items", "name_item", item, "quantity");
        int currentQty = Integer.parseInt(qtyItem.get(0).toString());

        int result = currentQty + quantity;
        Database.updateData("items", "quantity", result, "name_item", item);
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
