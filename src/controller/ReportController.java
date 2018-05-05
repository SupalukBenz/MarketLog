package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import program.Database;
import program.SalesDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportController {

    @FXML
    private AnchorPane pane;

    @FXML
    private BarChart<String, Double> salesChartMonth;

    @FXML
    private BarChart<Integer, Double> salesChartYear;

    @FXML
    public void initialize(){

    }

    private void loadSalesChartMonth(){
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        ResultSet rs = Database.getAllData("sales");
        String[] dateSplit;
        try {
            while (rs.next()){
                dateSplit = rs.getString("date_sale").split("-");

                ResultSet resultSet = Database.getAllData("sale_id_details");
                double amountSale = 0;

//                try {
//                    while (rs.next()){
//                        if(rs.getString("id_detail").equals(String.valueOf(receipt))) {
//
//                            amountSale += rs.getInt("total_detail");
//                        }
//                    }
//
//                }catch (SQLException se){
//                    se.printStackTrace();
//                }

//                series.getData().add(new XYChart.Data<>(convertToMonth(dateSplit[1]),rs.getDouble("")));

            }

        } catch (SQLException se){
            se.printStackTrace();
        }
    }

//    private String convertToMonth(String data) {
//        if (data.equals("01")) return "January";
//        else if (data.equals("02")) return "February";
//        else if (data.equals("03")) return "March";
//        else if (data.equals("04")) return "April";
//        else if (data.equals("05")) return "May";
//        else if (data.equals("06")) return "June";
//        else if (data.equals("07")) return "July";
//        else if (data.equals("08")) return "August";
//        else if (data.equals("09")) return "September";
//        else if (data.equals("10")) return "October";
//        else if (data.equals("11")) return "November";
//        else if (data.equals("12")) return "December";' '
//    }
}
