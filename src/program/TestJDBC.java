package program;


import java.time.LocalDate;
import java.util.Date;

public class TestJDBC {
    public static void main(String[] args) {
        String tableName = "sales";

        Database.insertData(tableName, LocalDate.now(), 11102, "ME", 1, 150.0, 0);

    }
}
