package main.dataAccess;

import main.exception.ConnectionException;
import main.exception.CustomerException;
import main.model.Customer;
import main.model.Locality;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO implements CustomerDAOInterface {
    private final Connection connection;

    public CustomerDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion à l'employé", exception);
        }
    }


    public ArrayList<Customer> readAll() throws CustomerException {
        ArrayList<Customer> customers = new ArrayList<>();
        String sqlInstruction = "SELECT * FROM customer";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction); ResultSet data = preparedStatement.executeQuery()) {
            Integer phoneNumber;
            Integer fidelityPointsNb;
            Locality locality;
            Customer customer;
            while (data.next()) {
                locality = new Locality(data.getString("region_postal_code"), data.getString("region_locality"));
                customer = new Customer(data.getInt("id"), data.getString("lastname"), data.getString("firstname"), data.getString("address"), data.getString("email"), locality);

                phoneNumber = data.getInt("phone_number");
                if (!data.wasNull()) {
                    customer.setPhoneNumber(phoneNumber);
                }

                fidelityPointsNb = data.getInt("fidelity_point_nb");
                if (!data.wasNull()) {
                    customer.setFidelityPointNb(fidelityPointsNb);
                }

                customers.add(customer);
            }

            return customers;
        } catch (SQLException exception) {
            throw new CustomerException("Erreur lors de la lecture des clients");
        }
    }

    public Customer getCustomerById(int id) throws CustomerException {
        Customer customer;
        String sqlInstruction = "SELECT id, lastname, firstname, address, email, phone_number, fidelity_point_nb, region_postal_code, region_locality FROM customer WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)) {
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery()) {
                if (!data.next()) {
                    throw new CustomerException("Le client n'existe pas :");
                }

                Locality locality = new Locality(data.getString("region_postal_code"), data.getString("region_locality"));
                customer = new Customer(data.getInt("id"), data.getString("lastname"), data.getString("firstname"), data.getString("address"), data.getString("email"), locality);
                return customer;
            }
        }catch (SQLException exception)
        {
            throw new CustomerException("Erreur lors de la lecture du client");
        }

    }
}
