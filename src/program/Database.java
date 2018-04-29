package program;

import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.*;
import com.mysql.jdbc.*;

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

    public static void insertData(String tableName, Object ... values){
        Statement statement = null;
        connectDatabase();

        try {
            Class.forName(JDBC_DRIVER);

            statement = connection.createStatement();
            String sql = "insert into " + tableName;
            sql += createStatement(values);
            System.out.println(sql);
            statement.execute(sql);
        } catch (SQLException se){
            System.out.println("Cannot try to insert data." + se);
        } catch (Exception e){
            e.printStackTrace();
        }
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
        data.append(" values(");
        for (Object value : values) {
            if(value.getClass().equals(String.class)) data.append("\"").append(value).append("\"");
            else if(value.getClass().equals(LocalDate.class)) data.append("\'").append(value).append("\'");
            else data.append(value);
            if(!value.equals(values[values.length - 1])) data.append(", ");
        }
        data.append(")");

        return data.toString();
    }



}
