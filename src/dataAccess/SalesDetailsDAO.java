package dataAccess;

import exception.SalesDetailsException;
import model.SalesDetails;

import exception.ConnectionException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class SalesDetailsDAO implements DataAccessInterface<SalesDetails>
{
    private final Connection connection;

    public SalesDetailsDAO() throws ConnectionException
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

    public void create(SalesDetails salesDetails) throws SalesDetailsException
    {

        String sqlInstruction = "INSERT INTO sales_detail (salesDetails)" + "VALUES(?)";

    }

    @Override
    public void delete(int id) throws SalesDetailsException
    {
        String sqlInstruction = "DELETE FROM sales_detail WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur lors de la suppression", exception);
        }

    }


    @Override
    public SalesDetails read(int id) throws SalesDetailsException
    {
        String sqlInstruction = "SELECT id, quantity, fidelity_points_used, payment_method, comment, date, buyer_id, seller_id " + "FROM sales_detail WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("quantity");
                    boolean fidelityUsed = resultSet.getBoolean("fidelity_points_used");
                    String paymentMethod = resultSet.getString("payment_method");
                    String comment = resultSet.getString("comment");
                    LocalDateTime date = resultSet.getObject("date", LocalDateTime.class);
                    int buyerId = resultSet.getInt("buyer_id");
                    int sellerId = resultSet.getInt("seller_id");

                    return new SalesDetails(id, quantity, fidelityUsed, paymentMethod, comment, date, buyerId, sellerId);
                }
            }
        } catch (SQLException exception) {
            throw new SalesDetailsException("Erreur lors de la lecture de SalesDetails", exception);
        }
    }


    public void update(SalesDetails salesDetails) throws SalesDetailsException, ConnectionException
    {
        String sqlInstruction = "UPDATE sales_details SET quantity = ?, fidelity_point_used = ?, payment_method = ?, comment = ?, date = ?, buyer_id = ?, seller_id = ? WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, salesDetails.getQuantity());
            preparedStatement.setBoolean(2, salesDetails.isFidelityPointUsed());
            preparedStatement.setString(3, salesDetails.getPaymentMethod());
            preparedStatement.setString(4, salesDetails.getComment());
            preparedStatement.setObject(5, salesDetails.getDate());
            preparedStatement.setObject(6, salesDetails.getBuyer());
            preparedStatement.setObject(7, salesDetails.getSeller());
        }
        catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur de connexion", exception);
        }

    }

    @Override
    public List<SalesDetails> readAll() throws ConnectionException {
        return List.of();
    }
}
