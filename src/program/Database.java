package program;

import java.sql.*;

public class Database {
    public void connectDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:8080/market_log", "root", "");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `user`");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
