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

    static final String USER = PropertyManager.getProperty("jdbc.user");
    static final String PASS = PropertyManager.getProperty("jdbc.password");

    static final String JDBC_DRIVER = PropertyManager.getProperty("jdbc.driver");
    static final String DB_URL = PropertyManager.getProperty("jdbc.url");

    static Connection connection = null;


    public static void connectDatabase(){
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e){
            System.out.println("Cannot try to connect database.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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



    public static void deleteAllData(String tableName){
        Statement statement = null;
        connectDatabase();

        try{
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
