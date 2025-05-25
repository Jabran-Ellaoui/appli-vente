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

            while (data.next())
            {
                product = new Product(data.getInt("id"), data.getDouble("unitPrice"), data.getObject("model", ProductModel.class), data.getObject("sales", SalesDetails.class));

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

}
