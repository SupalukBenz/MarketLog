package program;

import javafx.collections.ObservableList;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class EditValue {
    private static int qtySales = 0;
    public static void editingQtyOfItems(String item, int orderQty){
        ObservableList qtyItem = Database.searchFromKey("items", "name_item", item, "quantity");
        int currentQty = Integer.parseInt(qtyItem.get(0).toString());

        int result = currentQty - orderQty;
        Database.updateData("items", "quantity", result, "name_item", item);
    }

    public static void inventoryUpdate(ObservableList<Order> orderList){
        List<String> itemName = new ArrayList<>();
        List<Integer> qtyOrder = new ArrayList<>();
        for(int i = 0; i <= orderList.size() -1; i++){
            itemName.add(orderList.get(i).getItem());
            qtyOrder.add(orderList.get(i).getQuantity());
        }
        for(int j = 0; j <= itemName.size()-1; j++){
            editingQtyOfItems(itemName.get(j), qtyOrder.get(j));
        }
    }

    public static void addDetailItems(int receipt){
        ObservableList item = Database.selectItem("orders", "item_order");
        ObservableList descrip = Database.selectItem("orders", "description_order");
        ObservableList qty = Database.selectItem("orders", "qty_order");
        ObservableList total = Database.selectItem("orders", "total_order");
        int q = 0;
        for(Object i: item){
            Database.insertData("sale_id_details", getNumberDetail(), receipt, item.get(q), descrip.get(q), qty.get(q), total.get(q));
            qtySales += Integer.parseInt((String) qty.get(q));
            q++;
        }
    }

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


    public static int getQtySales(){
        return qtySales;
    }

    public static int getReceiptId(){
        ObservableList id = Database.selectItem("sales", "receipt_id");

        if(id == null || id.isEmpty()){
            return 10001;
        }else{
            int currentId = Integer.parseInt((String) id.get(id.size() - 1));
            return currentId + 1;
        }
    }

    public static int getNumberDetail(){
        ObservableList id = Database.selectItem("sale_id_details", "number_detail");

        if(id == null || id.isEmpty()){
            return 1;
        }else{
            int currentId = Integer.parseInt((String) id.get(id.size() - 1));
            return currentId + 1;
        }
    }


}
