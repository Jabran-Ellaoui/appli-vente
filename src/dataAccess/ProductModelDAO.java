package dataAccess;

import exception.ConnectionException;
import model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductModelDAO implements DataAccessInterface
{
    private final Connection connection;

    public ProductModelDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(ProductModel product) throws ConnectionException
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
            throw new ConnectionException("Connexion impossible", exception);
        }
    }

    @Override
    public void delete(int barcode) throws ConnectionException {
        String sqlInstruction = "DELETE FROM ProductModel WHERE barcode = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, barcode);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception)
        {
            throw new ConnectionException("Connexion impossible", exception);
        }

    }


    @Override
    public ProductModel read(int id)
    {
        return null;
    }

    @Override
    public void update(ProductModel product) throws ConnectionException {
        String sqlInstruction = "UPDATE product_model SET label=?, eco_score = ?, fidelity_point_nb=?, required_age=?, kept_warm=?, kept_cold=?, expiration_date=?, weight=?, storage_temperature=?, provenance=? WHERE barcode=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)) {
            preparedStatement.setString(1, product.getLabel());
            preparedStatement.setString(2, product.getEcoScore());
            preparedStatement.setInt(3, product.getFidelityPointNb());
            preparedStatement.setObject(4, product.getRequiredAge(), Types.INTEGER);
            preparedStatement.setBoolean(5, product.isKeptWarm());
            preparedStatement.setBoolean(6, product.isKeptCold());
            preparedStatement.setDate(7, Date.valueOf(product.getExpirationDate()));
            preparedStatement.setDouble(8, product.getWeight());
            preparedStatement.setObject(9, product.getStorageTemperature(), Types.DOUBLE);
            preparedStatement.setObject(10, product.getProvenance());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ConnectionException("Error updating ProductModel", e);
        }
    }

    @Override
    public List<ProductModel> readAll() throws ConnectionException {
        String sqlInstruction = "SELECT * FROM ProductModel";


        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            List<ProductModel> products = new ArrayList<>();
            ResultSet data = preparedStatement.executeQuery();
            ProductModel productModel;
            Integer requiredAge;
            Integer storageTemperature;
            String ecoScore;
            while (data.next()) {
                productModel = new ProductModel(data.getInt("barcode"), data.getString("label"), data.getInt("fidelity_point_nb"), data.getBoolean("keep_warm"), data.getBoolean("keep_cold"), data.getDate("expiration_date").toLocalDate(), data.getDouble("weight"), data.getInt("provenance"));

            }

        } catch (SQLException e) {
            throw new ConnectionException("Error updating ProductModel", e);
        }


        return List.of();
    }

}