package dataAccess;

import exception.ConnectionException;
import exception.ProductException;
import model.Product;
import model.ProductModel;
import model.SalesDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO implements ProductDAOInterface
{
    private final Connection connection;

    public ProductDAO() throws ConnectionException
    {
        try
        {
            this.connection = DatabaseConnection.getInstance().getConnection();
        }
        catch (SQLException exception)
        {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public ArrayList<Product> readAll() throws ProductException
    {
        ArrayList<Product> products = new ArrayList<>();
        String sqlInstruction = "SELECT * FROM product";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            ResultSet data = preparedStatement.executeQuery();
            Product product;
            Integer promotionPercentage;
            ProductModel productModel;
            SalesDetails salesDetails;

            while (data.next())
            {
                productModel = new ProductModel(data.getInt("model_barcode"));
                salesDetails = new SalesDetails(data.getInt("sale"));
                product = new Product(data.getInt("id"), data.getDouble("unit_price"), productModel, salesDetails);

                promotionPercentage = data.getInt("promotion_percentage");
                if (!data.wasNull()) {product.setPromotionPercentage(promotionPercentage);};
                products.add(product);
            }
            return products;
        }
        catch (SQLException exception)
        {
            throw new ProductException("Erreur lors de la lecture des produits", exception);
        }
    }

    public void deleteAllProductsFromSales(int salesId) throws SQLException
    {

        String sqlInstruction = "DELETE FROM product WHERE sale = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlInstruction))
        {
            ps.setInt(1, salesId);
            ps.executeUpdate();
        }

        String sqlInstruction2 = "DELETE FROM sales_detail WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sqlInstruction2))
        {
            ps.setInt(1, salesId);
            ps.executeUpdate();
        }
    }

    public ArrayList<Product> readProductsBySalesID(int saleId) throws ProductException {
        ArrayList<Product> products = new ArrayList<>();
        String sqlInstruction = "SELECT * FROM product WHERE sale = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction)) {
            preparedStatement.setInt(1, saleId);
            ResultSet data = preparedStatement.executeQuery();

            Product product;
            Integer promotionPercentage;
            ProductModel productModel;
            SalesDetails salesDetails;

            while (data.next()) {
                productModel = new ProductModel(data.getInt("model_barcode"));
                salesDetails = new SalesDetails(data.getInt("sale"));
                product = new Product(data.getInt("id"), data.getDouble("unit_price"), productModel, salesDetails);

                promotionPercentage = data.getInt("promotion_percentage");
                if (!data.wasNull()) {
                    product.setPromotionPercentage(promotionPercentage);
                }
                products.add(product);
            }
            return products;
        } catch (SQLException exception) {
            throw new ProductException("Erreur lors de la lecture des produits pour le saleId = " + saleId, exception);
        }
    }

}
