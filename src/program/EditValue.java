package program;

import javafx.collections.ObservableList;

import javax.xml.crypto.Data;

public class EditValue {
    private static int qtySales = 0;
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

    public static String compoundItems(){
        ObservableList item = Database.selectItem("orders", "item_order");
        ObservableList descrip = Database.selectItem("orders", "description_order");
        ObservableList qty = Database.selectItem("orders", "qty_order");

        StringBuilder sales = new StringBuilder();
        int q = 0;
        for(Object i: item){
            sales.append(i + "[");
            sales.append(descrip.get(q) + "]");
            if(q < item.size() - 1) sales.append(";");
            qtySales += Integer.parseInt((String) qty.get(q));
            q++;
        }
        return sales.toString();
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


}
