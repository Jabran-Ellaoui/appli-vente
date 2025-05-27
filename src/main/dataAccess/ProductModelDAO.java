package main.dataAccess;

import main.exception.ConnectionException;
import main.exception.ProductModelException;
import main.model.Lot;
import main.model.ProductModel;

import java.sql.*;
import java.util.ArrayList;

public class ProductModelDAO implements ProductModelDAOInterface {
    private final Connection connection;

    public ProductModelDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(ProductModel product) throws ProductModelException {
        String sqlInstruction = "INSERT INTO product_model (barcode, label, fidelity_point_nb, kept_warm, kept_cold, expiration_date, weight, provenance) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            Lot provenance = product.getProvenance();
            preparedStatement.setInt(8, provenance.getNumber());
            preparedStatement.executeUpdate();

            if(product.getStorageTemperature() != null) {
                String sqlInstructionStorageTemperature = "update product_model set storage_temperature = ? where barcode = ? ";
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
    public ProductModel read(int id) throws ProductModelException
    {
        String sqlInstruction = "SELECT barcode, label, fidelity_point_nb, kept_warm, kept_cold, expiration_date, weight, provenance, required_age, storage_temperature, eco_score FROM product_model WHERE barcode = ?";
        ProductModel productModel;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery())
            {
                if (!data.next())
                {
                    throw new ProductModelException("Le produit n'existe pas :" + id);
                }

                Integer requiredAge;
                Integer storageTemperature;
                String ecoScore;
                Lot provenance = new Lot(data.getInt("provenance"));

                productModel = new ProductModel(data.getInt("barcode"), data.getString("label"), data.getInt("fidelity_point_nb"), data.getBoolean("kept_warm"), data.getBoolean("kept_cold"), data.getDate("expiration_date").toLocalDate(), data.getDouble("weight"), provenance);

                requiredAge = data.getInt("required_age");
                if(!data.wasNull()) {productModel.setRequiredAge((Integer) requiredAge);}

                storageTemperature = data.getInt("storage_temperature");
                if(!data.wasNull()) {productModel.setStorageTemperature((Integer) storageTemperature);}

                ecoScore = data.getString("eco_score");
                if(!data.wasNull()) {productModel.setEcoScore(ecoScore);}

                return productModel;
            }
        }
        catch (SQLException productModelException)
        {
            throw new ProductModelException("Erreur lors de la lecture du produit", productModelException);
        }
    }

    // changer les types integer à mettre dans la bd !!!!!!!!!!!!!!!!!!!!!! + vérifier les exceptions
    @Override
    public void update(ProductModel product) throws ProductModelException
    {
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
            Lot provenance = product.getProvenance();
            preparedStatement.setInt(10, provenance.getNumber());
            preparedStatement.setInt(11, product.getBarcode());
            preparedStatement.executeUpdate();
        }
        catch (SQLException productModelException)
        {
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
        catch (SQLException productModelException)
        {
            throw new ProductModelException("Erreur lors de la suppression du produit", productModelException);
        }

    }

    @Override
    public ArrayList<ProductModel> readAll() throws ProductModelException {
        String sqlInstruction = "SELECT * FROM product_model";
        ArrayList<ProductModel> products = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction); ResultSet data = preparedStatement.executeQuery())
        {
            Integer requiredAge;
            Integer storageTemperature;
            String ecoScore;
            ProductModel productModel;
            Lot provenance;
            while (data.next())
            {
                provenance = new Lot(data.getInt("provenance"));
                productModel = new ProductModel(data.getInt("barcode"), data.getString("label"), data.getInt("fidelity_point_nb"), data.getBoolean("kept_warm"), data.getBoolean("kept_cold"), data.getDate("expiration_date").toLocalDate(), data.getDouble("weight"), provenance);
                requiredAge = data.getInt("required_age");
                if(!data.wasNull()) {productModel.setRequiredAge((Integer) requiredAge);}

                storageTemperature = data.getInt("storage_temperature");
                if(!data.wasNull()) {productModel.setStorageTemperature((Integer) storageTemperature);}

                ecoScore = data.getString("eco_score");
                if(!data.wasNull()) {productModel.setEcoScore(ecoScore);}

                products.add(productModel);
            }
            return products;
        } catch (SQLException productModelException)
        {
            throw new ProductModelException("Erreur lors de la lecture des produits", productModelException);
        }
    }

}