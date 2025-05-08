import dataAccess.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args)
    {
        try {
            Connection connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","1234");
        }
        catch (SQLException exception)
        {

        }

    }
}