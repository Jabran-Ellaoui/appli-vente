package dataAccess;

import exception.ConnectionException;
import exception.ProductModelException;
import model.Lot;
import model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductModelDAO implements DataAccessInterface<ProductModel>
{
    private final Connection connection;

    public ProductModelDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(ProductModel product) throws ProductModelException
    {
        // Création requête
        String sqlInstruction = "INSERT INTO product_model (barcode, label, fidelity_points_nb, required_age, kept_warm, kept_cold, expiration_date, weight, storage_temperature, provenance) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            // Remplissage de l'objet
            preparedStatement.setInt(1, product.getBarcode());
            preparedStatement.setString(2, product.getLabel());
            preparedStatement.setInt(3, product.getFidelityPointNb());
            preparedStatement.setObject(4, product.getRequiredAge(), Types.INTEGER);
            preparedStatement.setBoolean(5, product.isKeptWarm());
            preparedStatement.setBoolean(6, product.isKeptCold());
            preparedStatement.setDate(7, Date.valueOf(product.getExpirationDate()));
            preparedStatement.setDouble(8, product.getWeight());
            preparedStatement.setObject(9, product.getStorageTemperature(), Types.DOUBLE);
            preparedStatement.setObject(10, product.getProvenance());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            throw new ProductModelException("Erreur lors de la création du produit", exception);
        }
    }

    @Override
    public void delete(int barcode) throws ProductModelException
    {
        String sqlInstruction = "DELETE FROM product_model WHERE barcode = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, barcode);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception)
        {
            throw new ProductModelException("Suppression impossible", exception);
        }

    }


    @Override
    public ProductModel read(int id)
    {
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
            throw new ConnectionException("Erreur lors de la mise à jour du produit", e);
        }
    }

    @Override
    public List<ProductModel> readAll() throws ConnectionException
    {
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
            throw new ConnectionException("Erreur lors de la lecture du produit", e);
        }


        return List.of();
    }

}