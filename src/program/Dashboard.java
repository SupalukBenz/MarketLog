package program;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 *
 * @author Supaluk
 */
public class Dashboard {
    private static double totalSale = 0;
    private static int orderTotal = 0;
    private static int orderPaid = 0;
    private static int orderUnpaid = 0;
    private static int unpaidTotal = 0;

    private static int itemsStock = 0;
    private static int itemStockZero = 0;

    public static void getSalesTotal(){
        ResultSet rs = Database.getAllData("sales");

        try {
            while(rs.next()){
                if(rs.getString("status_sale").equals("paid")) {
                    orderPaid++;
                    totalSale += rs.getDouble("total_sale");
                }
                if(rs.getString("status_sale").equals("unpaid")) {
                    orderUnpaid++;
                    unpaidTotal += rs.getDouble("total_sale");

                }
                orderTotal++;
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }

    public static int getUnpaidTotal() {
        return unpaidTotal;
    }

    public static int getOrderTotal() {
        return orderTotal;
    }

    public static double getTotalSale() {
        return totalSale;
    }

    public static int getOrderPaid() {
        return orderPaid;
    }

    public static int getOrderUnpaid() {
        return orderUnpaid;
    }

    public static int getItemsStock() {
        return itemsStock;
    }

    public static int getItemStockZero() {
        return itemStockZero;
    }

    public static void getItemsTotal(){
        ResultSet rs = Database.getAllData("items");
        try {
            while(rs.next()){
                if(rs.getInt("quantity") == 0){
                    itemStockZero++;
                }else {
                    itemsStock++;

                }
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
    }


}
