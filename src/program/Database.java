package program;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.*;
import com.mysql.jdbc.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 *
 * @author Supaluk
 */

public class Database {

    static final String USER = "benz";
    static final String PASS = "benz9545";

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/market_log";

    static Connection connection = null;


    public static void connectDatabase(){
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e){
            System.out.println("Cannot try to connect database.");
        }
    }

    public static void closeDatabase(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException se){
                System.out.println("Cannot try to close database.");
                se.printStackTrace();
            }
        }
    }

    public static ObservableList selectItem(String tableName, String columnName){
        ObservableList observableList = FXCollections.observableArrayList();
        PreparedStatement pre = null;
        ResultSet rs = null;
        String sql = "select " + columnName + " from " + tableName;
        try {
            connectDatabase();
            pre = connection.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()){
                observableList.add(rs.getString(columnName));
            }
        } catch (SQLException se){
            System.out.println("Cannot select data from database");
            se.printStackTrace();
        }
        return observableList;
    }

    public static ObservableList searchFromKey(String tableName, String key, String value, String result){
        ObservableList observableList = FXCollections.observableArrayList();
        PreparedStatement pre = null;
        ResultSet rs = null;
        String sql = "select " + result + " from " + tableName + " where " + key + getName(value);
        try {
            connectDatabase();
            pre = connection.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()){
                observableList.add(rs.getString(result));
            }
        } catch (SQLException se){
            se.printStackTrace();
        }
        return observableList;
    }

    public static ObservableList searchFromKeyId(String tableName, String key, int value, String result){
        ObservableList observableList = FXCollections.observableArrayList();
        PreparedStatement pre = null;
        ResultSet rs = null;
        String sql = "select " + result + " from " + tableName + " where " + key + "=" + value;
        try {
            connectDatabase();
            pre = connection.prepareStatement(sql);
            rs = pre.executeQuery();
            while (rs.next()){
                observableList.add(rs.getString(result));
            }
        } catch (SQLException se){
            System.out.println("Cannot search ID");
            se.printStackTrace();
        }
        return observableList;
    }


    public static void insertData(String tableName, Object ... values){
        Statement statement = null;
        connectDatabase();

        try {
            Class.forName(JDBC_DRIVER);

            statement = connection.createStatement();
            String sql = "insert into " + tableName;
            sql += " values(";
            sql += createStatement(values);
            sql += ")";
            System.out.println(sql);
            statement.execute(sql);
        } catch (SQLException se){
            System.out.println("Cannot try to insert data." + se);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteData(String tableName, String primaryKey, String value){
        Statement statement = null;
        connectDatabase();

        try{
            Class.forName(JDBC_DRIVER);
            statement = connection.createStatement();

            String sql = "delete from " + tableName + " where " + primaryKey + getName(value);
            System.out.println(sql);
            statement.execute(sql);
        }catch (SQLException se){
            System.out.println("Cannot select data.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void deleteAllData(String tableName){
        Statement statement = null;
        connectDatabase();

        try{
            Class.forName(JDBC_DRIVER);
            statement = connection.createStatement();

            String sql = "delete from " + tableName;
            System.out.println(sql);
            statement.execute(sql);
        }catch (SQLException se){
            System.out.println("Cannot select data.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void updateData(String tableName, String columnEdit, Object edit, String columnKey, Object key){
        Statement statement = null;
        connectDatabase();

        try{
            Class.forName(JDBC_DRIVER);
            statement = connection.createStatement();
            String sql = "UPDATE " + tableName + " SET " + columnEdit + "=" + createStatement(edit) + " where " + columnKey+"="+createStatement(key);
            System.out.println(sql);
            statement.execute(sql);
        }catch (SQLException se){
            System.out.println("Cannot select data.");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String getName(String value){
        StringBuilder data = new StringBuilder();
        data.append("=\"");
        data.append(value);
        data.append("\"");
        return data.toString();
    }

    public static ResultSet getAllData(String tableName){
        ResultSet resultSet = null;
        try {
            connectDatabase();
            resultSet = connection.createStatement().executeQuery("select * from " + tableName);

        } catch (SQLException se){
            System.out.println("Cannot return ResultSet");
        }

        return resultSet;

    }

    public static String createStatement(Object ... values){
        StringBuilder data = new StringBuilder();
        for (Object value : values) {
            if(value.getClass().equals(String.class)) data.append("\"").append(value).append("\"");
            else if(value.getClass().equals(LocalDate.class)) data.append("\'").append(value).append("\'");
            else data.append(value);
            if(!value.equals(values[values.length - 1])) data.append(", ");
        }

        return data.toString();
    }



}
