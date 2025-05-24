package dataAccess;

import exception.ConnectionException;
import exception.EmployeeException;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO implements EmployeeDAOInterface {
    private final Connection connection;

    public EmployeeDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion à l'employé", exception);
        }
    }

    public ArrayList<Employee> readAll() throws EmployeeException {
        ArrayList<Employee> employees = new ArrayList<>();
        String sqlInstruction = "SELECT * FROM employee";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction); ResultSet data = preparedStatement.executeQuery())
        {
            while (data.next())
            {
                employees.add(new Employee(data.getInt("id"), data.getString("lastname"), data.getString("firstname"), data.getInt("phone_number")));
            }
            return employees;
        }
        catch (SQLException exception)
        {
            throw new EmployeeException("Erreur lors de la lecture des employés", exception);
        }
    }

}
