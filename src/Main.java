import dataAccess.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
        DatabaseConnection db = new DatabaseConnection();
        try (Connection connection = db.getInstance()) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}