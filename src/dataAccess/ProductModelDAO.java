package dataAccess;

import exception.ConnectionException;
import exception.ProductModelException;
import model.Lot;
import model.ProductModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductModelDAO implements DataAccessInterface<ProductModel> {
    private final Connection connection;

    public ProductModelDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(ProductModel product) throws ProductModelException {
        String sqlInstruction = "INSERT INTO product_model (barcode, label, fidelity_points_nb, kept_warm, kept_cold, expiration_date, weight, provenance) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            // Remplissage objet valeurs obligatoires
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            preparedStatement.setInt(1, product.getBarcode());
            preparedStatement.setString(2, product.getLabel());
            preparedStatement.setInt(3, product.getFidelityPointNb());
            preparedStatement.setBoolean(4, product.isKeptWarm());
            preparedStatement.setBoolean(5, product.isKeptCold());
            preparedStatement.setDate(6, Date.valueOf(product.getExpirationDate()));
            preparedStatement.setDouble(7, product.getWeight());
            preparedStatement.setObject(8, product.getProvenance());
            preparedStatement.executeUpdate();

            if(product.getStorageTemperature() != null) {
                String sqlInstructionStorageTemperature = "update product_model set storage_temparature = ? where barcode = ? ";
                preparedStatement = connection.prepareStatement(sqlInstructionStorageTemperature);
                preparedStatement.setInt(1, product.getStorageTemperature());
                preparedStatement.setInt(2, product.getBarcode());
                preparedStatement.executeUpdate();
            }

            if(product.getRequiredAge() != null) {
                String sqlInstructionRequiredAge = "update product_model set required_age = ? where barcode = ? ";
                preparedStatement = connection.prepareStatement(sqlInstructionRequiredAge);
                preparedStatement.setInt(1, product.getRequiredAge());
                preparedStatement.setInt(2, product.getBarcode());
                preparedStatement.executeUpdate();
            }

            if(product.getEcoScore() != null) {
                String sqlInstructionEcoScore = "update product_model set eco_score = ? where barcode = ? ";
                preparedStatement = connection.prepareStatement(sqlInstructionEcoScore);
                preparedStatement.setString(1, product.getEcoScore());
                preparedStatement.setInt(2,product.getBarcode());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException productModelException) {
            throw new ProductModelException("Erreur lors de la création du produit", productModelException);
        }
    }
    @Override
    public ProductModel read(int id) throws ConnectionException, ProductModelException {
        String sqlInstruction = "SELECT * FROM product_model WHERE barcode = ?";
        ProductModel productModel;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
            ResultSet data = preparedStatement.executeQuery();
            Integer requiredAge;
            Integer storageTemperature;
            String ecoScore;
            data.next();
            productModel = new ProductModel(data.getInt("barcode"), data.getString("label"), data.getInt("fidelity_point_nb"), data.getBoolean("keep_warm"), data.getBoolean("keep_cold"), data.getDate("expiration_date").toLocalDate(), data.getDouble("weight"), data.getInt("provenance"));
            requiredAge = data.getInt("required_age");
            if(!data.wasNull()) {
                productModel.setRequiredAge((Integer) requiredAge);
            }
            storageTemperature = data.getInt("storage_temperature");
            if(!data.wasNull()) {
                productModel.setStorageTemperature((Integer) storageTemperature);
            }
            ecoScore = data.getString("eco_score");
            if(!data.wasNull()) {
                productModel.setEcoScore(ecoScore);
            }
        } catch (SQLException productModelException) {
            throw new ProductModelException("Erreur lors de la lecture du produit", productModelException);
        }
        return productModel;
    }
    @Override
    public void update(ProductModel product) throws ProductModelException {
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
        } catch (SQLException productModelException) {
            throw new ProductModelException("Erreur lors de la mise à jour du produit", productModelException);
        }
    }
    @Override
    public void delete(int barcode) throws ProductModelException {
        String sqlInstruction = "DELETE FROM product_model WHERE barcode = ? ";
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
    public List<ProductModel> readAll() throws ConnectionException, ProductModelException {
        String sqlInstruction = "SELECT * FROM product_model";
        ArrayList<ProductModel> products = new ArrayList<>();
        // à voir si on met les preparedstatement dans les try ?
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            ResultSet data = preparedStatement.executeQuery();
            ProductModel productModel;
            Integer requiredAge;
            Integer storageTemperature;
            String ecoScore;
            while (data.next()) {
                productModel = new ProductModel(data.getInt("barcode"), data.getString("label"), data.getInt("fidelity_point_nb"), data.getBoolean("keep_warm"), data.getBoolean("keep_cold"), data.getDate("expiration_date").toLocalDate(), data.getDouble("weight"), data.getInt("provenance"));
                requiredAge = data.getInt("required_age");
                if(!data.wasNull()) {
                    productModel.setRequiredAge((Integer) requiredAge);
                }
                storageTemperature = data.getInt("storage_temperature");
                if(!data.wasNull()) {
                    productModel.setStorageTemperature((Integer) storageTemperature);
                }
                ecoScore = data.getString("eco_score");
                if(!data.wasNull()) {
                    productModel.setEcoScore(ecoScore);
                }
                products.add(productModel);
            }
        } catch (SQLException productModelException) {
            throw new ProductModelException("Erreur lors de la lecture des produits", productModelException);
        }
        return products;
    }

}