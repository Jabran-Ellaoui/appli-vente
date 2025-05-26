package dataAccess;

import exception.ConnectionException;
import exception.CustomerException;
import model.Customer;
import model.Locality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO implements CustomerDAOInterface
{
    private final Connection connection;

    public CustomerDAO() throws ConnectionException
    {
        try
        {
            this.connection = DatabaseConnection.getInstance().getConnection();
        }
        catch (SQLException exception)
        {
            throw new ConnectionException("Erreur de connexion à l'employé", exception);
        }
    }


    public ArrayList<Customer> readAll() throws CustomerException
    {
        ArrayList<Customer> customers = new ArrayList<>();
        String sqlInstruction = "SELECT * FROM customer";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction); ResultSet data = preparedStatement.executeQuery())
        {
            Integer phoneNumber;
            Integer fidelityPointsNb;
            Locality locality;
            Customer customer;
            while (data.next())
            {
                locality = new Locality(data.getString("region_postal_code"), data.getString("region_locality"));
                customer = new Customer(data.getInt("id"), data.getString("lastname"), data.getString("firstname"), data.getString("address"), data.getString("email"), locality);

                phoneNumber = data.getInt("phone_number");
                if (!data.wasNull()){customer.setPhoneNumber(phoneNumber);}

                fidelityPointsNb = data.getInt("fidelity_point_nb");
                if (!data.wasNull()){customer.setFidelityPointNb(fidelityPointsNb);}

                customers.add(customer);
            }

            return customers;
        }
        catch (SQLException exception)
        {
            throw new CustomerException("Erreur lors de la lecture des clients", exception);
        }
    }
}
