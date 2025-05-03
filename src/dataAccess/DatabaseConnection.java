package dataAccess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exception.ConnectionException;


public class DatabaseConnection
{
    /*
    * Cette classe permet de gérer la connexion à la base de données en créant une instance unique de la connexion
    * */

    private static Connection connection;

    public DatabaseConnection()
    {

    }

    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            connection =  DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "rootpassword1234");
            System.out.println("hello");
        }
        return connection;
    }

}