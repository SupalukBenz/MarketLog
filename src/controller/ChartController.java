package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import orm.DatabaseManager;
import orm.SalesDao;
import program.Sales;

import java.time.LocalDate;

/**
 * ChartController is controller of chart bar that showing sales of months and sales of years.
 *
 * @author Supaluk Jaroensuk
 */
public class ChartController {

    /**
     * AnchorPane of pane UI
     */
    @FXML
    private AnchorPane pane;

    /**
     * BarChart sales of months
     */
    @FXML
    private BarChart<String, Double> salesChartMonth;

    /**
     * BarChart sales of years.
     */
    @FXML
    private BarChart<String, Double> salesChartYear;

    /**
     * Text label for show current year.
     */
    @FXML
    private Label currentYear;

    /**
     * XYChart for add data of x-axis and y-axis sales of months.
     */
    private XYChart.Series<String, Double> seriesMonths = new XYChart.Series<>();

    /**
     * XYChart for add data of x-axis and y-axis sales of year.
     */
    private XYChart.Series<String, Double> seriesYear = new XYChart.Series<>();

    /**
     * DatabaseManager class
     */
    private DatabaseManager db;

    /**
     * SalesDao access object for handle all database operation.
     */
    private SalesDao salesDao = null;

    /**
     * Stored value's each months.
     */
    private double value1, value2, value3, value4, value5, value6, value7, value8, value9, value10, value11, value12 = 0;

    /**
     * Stored value's each years.
     */
    private double value18, value19, value20, value21, value22, value23 = 0;

    /**
     * Initialize DatabaseManager for create SalesDao and load bar chart.
     */
    @FXML
    public void initialize(){
        db = DatabaseManager.getInstance();
        salesDao = db.getSalesDao();

        loadSalesChartMonth();
        loadSalesChartYear();
    }

    /**
     * Check current year for showing in bar chart.
     * @param data is date in each sales detail.
     * @return
     */
    private boolean checkYear(String data){
        LocalDate date = LocalDate.now();
        String now = date.toString();
        String[] year = now.split("-");
        currentYear.setText("Sales, year " + year[0]);
        if(data.equals(year[0])) return true;
        return false;
    }

    /**
     * Loading data from sales database and showing in bar chart.
     */
    private void loadSalesChartMonth(){
        String[] dateSplit;

        for (Sales sales : salesDao) {
            if(sales.getStatus().equals("paid")) {
                dateSplit = sales.getDate().split("-");
                if(checkYear(dateSplit[0])) {
                    sumValue(dateSplit[1], sales.getTotal());
                }
            }
        }
        seriesMonths.setName("Total(Baht)");
        seriesMonths.getData().add(new XYChart.Data<>("January", value1));
        seriesMonths.getData().add(new XYChart.Data<>("February", value2));
        seriesMonths.getData().add(new XYChart.Data<>("March", value3));
        seriesMonths.getData().add(new XYChart.Data<>("April", value4));
        seriesMonths.getData().add(new XYChart.Data<>("May", value5));
        seriesMonths.getData().add(new XYChart.Data<>("June", value6));
        seriesMonths.getData().add(new XYChart.Data<>("July", value7));
        seriesMonths.getData().add(new XYChart.Data<>("August", value8));
        seriesMonths.getData().add(new XYChart.Data<>("September", value9));
        seriesMonths.getData().add(new XYChart.Data<>("October", value10));
        seriesMonths.getData().add(new XYChart.Data<>("November", value11));
        seriesMonths.getData().add(new XYChart.Data<>("December", value12));
        salesChartMonth.getData().add(seriesMonths);
    }

    /**
     * Loading data from sales database and showing in bar chart.
     */
    private void loadSalesChartYear(){
        String[] dateSplit;

        for (Sales sales : salesDao) {
            if(sales.getStatus().equals("paid")) {
                dateSplit = sales.getDate().split("-");
                sumValueYear(dateSplit[0], sales.getTotal());
            }
        }

        seriesYear.setName("Total(Baht)");
        seriesYear.getData().add(new XYChart.Data<>("2018", value18));
        seriesYear.getData().add(new XYChart.Data<>("2019", value19));
        seriesYear.getData().add(new XYChart.Data<>("2020", value20));
        seriesYear.getData().add(new XYChart.Data<>("2021", value21));
        seriesYear.getData().add(new XYChart.Data<>("2022", value22));
        seriesYear.getData().add(new XYChart.Data<>("2023", value23));

        salesChartYear.getData().add(seriesYear);
        
    }

    /**
     * Storing values in each month.
      * @param data is month of each sales detail.
     * @param valueSale is amount in that month.
     * @return total value in that month.
     */
    private double sumValue(String data, double valueSale) {
        if (data.equals("01")) return value1 += valueSale;
        else if (data.equals("02")) return value2 += valueSale;
        else if (data.equals("03")) return value3 += valueSale;
        else if (data.equals("04")) return value4 += valueSale;
        else if (data.equals("05")) return value5 += valueSale;
        else if (data.equals("06")) return value6 += valueSale;
        else if (data.equals("07")) return value7 += valueSale;
        else if (data.equals("08")) return value8 += valueSale;
        else if (data.equals("09")) return value9 += valueSale;
        else if (data.equals("10")) return value10 += valueSale;
        else if (data.equals("11")) return value11 += valueSale;
        else if (data.equals("12")) return value12 += valueSale;
        return 0;
    }

    /**
     * Storing values in each year.
     * @param data is year of each sales detail.
     * @param valueSale is amount in that year.
     * @return total value in that year.
     */
    private double sumValueYear(String data, double valueSale){
        if(data.equals("2018")) return value18 += valueSale;
        else if(data.equals("2019")) return value19 += valueSale;
        else if(data.equals("2020")) return value20 += valueSale;
        else if(data.equals("2021")) return value21 += valueSale;
        else if(data.equals("2022")) return value22 += valueSale;
        else if(data.equals("2023")) return value23 += valueSale;
        return 0;
    }


}
