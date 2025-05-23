package dataAccess;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import exception.ConnectionException;


public class DatabaseConnection
{
    private static DatabaseConnection instance;
    private static Connection connection;

    private DatabaseConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarche_db", "root", "rootpassword1234");
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.connection == null || instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}