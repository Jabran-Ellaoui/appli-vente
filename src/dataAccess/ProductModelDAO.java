package dataAccess;

import exception.ConnectionException;
import exception.ProductModelException;
import model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductModelDAO implements DataAccessInterface
{
    private final Connection connection;

    public ProductModelDAO(Connection connection) throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(ProductModel product) throws ProductModelException
    {
        String sql = "INSERT INTO ProductModel (barcode, label, FidelityPointNb, requiredAge, keptWarm, keptCold, expirationDate, weight, storageTemperature, provenance) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, product.getBarcode());
            stmt.setString(2, product.getLabel());
            stmt.setInt(3, product.getFidelityPointNb());
            stmt.setObject(4, product.getRequiredAge(), Types.INTEGER);
            stmt.setBoolean(5, product.isKeptWarm());
            stmt.setBoolean(6, product.isKeptCold());
            stmt.setDate(7, Date.valueOf(product.getExpirationDate()));
            stmt.setDouble(8, product.getWeight());
            stmt.setObject(9, product.getStorageTemperature(), Types.DOUBLE);
            stmt.setObject(10, product.getProvenance());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            throw new ProductModelException("Error inserting ProductModel", exception);
        }
    }

    // READ
    public ProductModel findByBarcode(String barcode) throws ProductModelException
    {
        String sql = "SELECT * FROM ProductModel WHERE barcode = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, barcode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                } else {
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new ProductModelException("Error fetching ProductModel", exception);
        }
    }
}