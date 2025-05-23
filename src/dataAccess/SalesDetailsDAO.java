package dataAccess;

import exception.SalesDetailsException;
import exception.ConnectionException;
import model.*;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SalesDetailsDAO implements SalesDetailsDAOInterface {
    private final Connection connection;

    public SalesDetailsDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(SalesDetails salesDetails) throws SalesDetailsException
    {

        String sqlInstruction = "INSERT INTO sales_details " + "(id, quantity, fidelity_point_used, sale_date, seller_id) " + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, salesDetails.getId());
            preparedStatement.setInt(2, salesDetails.getQuantity());
            preparedStatement.setBoolean(3, salesDetails.isFidelityPointUsed());
            preparedStatement.setDate(4, Date.valueOf(salesDetails.getDate()));
            preparedStatement.setInt(5, salesDetails.getSeller().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur lors de la création de l'objet", exception);
        }

        if (salesDetails.getBuyer() != null)
        {
            String sqlInstruction2 = "UPDATE sales_details SET buyer_id = ? WHERE id = ?";
            try (PreparedStatement preparedStatement2 = connection.prepareStatement(sqlInstruction2))
            {
                preparedStatement2.setInt(1, salesDetails.getBuyer().getId());
                preparedStatement2.setInt(2, salesDetails.getId());
                preparedStatement2.executeUpdate();
            }
            catch (SQLException exception)
            {
                throw new SalesDetailsException("Erreur lors de l'insertion de l'acheteur", exception);
            }
        }

        if (salesDetails.getPaymentMethod() != null)
        {
            String sqlInstruction3 = "UPDATE sales_details SET payment_method = ? WHERE id = ?";
            try (PreparedStatement preparedStatement3 = connection.prepareStatement(sqlInstruction3))
            {
                preparedStatement3.setString(1, salesDetails.getPaymentMethod());
                preparedStatement3.setInt(2, salesDetails.getId());
                preparedStatement3.executeUpdate();
            }
            catch (SQLException exception)
            {
                throw new SalesDetailsException("Erreur lors de l'insertion du moyen de paiement'", exception);
            }
        }

        if (salesDetails.getComment() != null)
        {
            String sqlInstruction4 = "UPDATE sales_details SET comment = ? WHERE id = ?";
            try (PreparedStatement preparedStatement4 = connection.prepareStatement(sqlInstruction4))
            {
                preparedStatement4.setString(1, salesDetails.getComment());
                preparedStatement4.setInt(2, salesDetails.getId());
                preparedStatement4.executeUpdate();
            }
            catch (SQLException exception)
            {
                throw new SalesDetailsException("Erreur lors de l'insertion du commentaire", exception);
            }
        }
    }

    @Override
    public void delete(int id) throws SalesDetailsException
    {
        String sql = "DELETE FROM sales_detail WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new SalesDetailsException("Erreur lors de la suppression", exception);
        }
    }

    public SalesDetails read(int id) throws SalesDetailsException {
        /*
        String sqlInstruction = "SELECT id, quantity, fidelity_point_used, sale_date, payment_method, comment, buyer_id, seller_id" + " FROM sales_details WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery())
            {
                if (data.next())
                {

                    int quantity = data.getInt("quantity");
                    boolean fidelity = data.getBoolean("fidelity_point_used");
                    Date saleDate = data.getDate("sale_date");
                    int sellerId = data.getInt("seller_id");
                    Employee seller = new Employee(sellerId);

                    String tmpPayment = data.getString("payment_method");
                    String payment = data.wasNull() ? null : tmpPayment;

                    String tmpComment = data.getString("comment");
                    String comment = data.wasNull() ? null : tmpComment;

                    int buyerId = data.getInt("buyer_id");
                    Customer buyer = data.wasNull() ? null : new Customer(buyerId);

                    return new SalesDetails(id, quantity, fidelity, payment, comment, new java.util.Date(saleDate.getTime()), buyer, seller);
                }
            }
            catch (SQLException exception)
            {
                throw new SalesDetailsException("Erreur lors de la lecture", exception);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        */
        return null;
    }

    public void update(SalesDetails salesDetails) throws SalesDetailsException
    {
        String sql = "UPDATE sales_detail SET quantity = ?, fidelity_points_used = ?, payment_method = ?, comment = ?, date = ?, buyer_id = ?, seller_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, salesDetails.getQuantity());
            preparedStatement.setBoolean(2, salesDetails.isFidelityPointUsed());
            preparedStatement.setString(3, salesDetails.getPaymentMethod());
            preparedStatement.setString(4, salesDetails.getComment());
            preparedStatement.setDate(5, Date.valueOf(salesDetails.getDate()));
            preparedStatement.setObject(6, salesDetails.getBuyer());
            preparedStatement.setObject(7, salesDetails.getSeller());
            preparedStatement.setInt(8, salesDetails.getId());

            preparedStatement.executeUpdate();
        }
        catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur lors de la mise à jour", exception);
        }
    }

    @Override
    public ArrayList<SalesDetails> readAll() throws SalesDetailsException
    {/*
        ArrayList<SalesDetails> detailsList = new ArrayList<>();
        String sqlInstruction = "SELECT id, quantity, fidelity_point_used, sale_date, payment_method, comment, buyer_id, seller_id " + "FROM sales_details";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction);
             ResultSet data = preparedStatement.executeQuery())
        {
            while (data.next())
            {
                int id = data.getInt("id");
                int quantity = data.getInt("quantity");
                boolean fidelity = data.getBoolean("fidelity_point_used");
                Date saleDate = data.getDate("sale_date");
                int sellerId = data.getInt("seller_id");
                Employee seller = new Employee(sellerId);

                String tmpPayment = data.getString("payment_method");
                String payment    = data.wasNull() ? null : tmpPayment;

                String tmpComment = data.getString("comment");
                String comment    = data.wasNull() ? null : tmpComment;

                int tmpBuyerId = data.getInt("buyer_id");
                Customer buyer = data.wasNull() ? null : new Customer(tmpBuyerId);

                detailsList.add(new SalesDetails(id, quantity, fidelity, payment, comment, new java.util.Date(saleDate.getTime()), buyer, seller));
            }
            return (ArrayList<SalesDetails>) detailsList;
        }
        catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur lors de la lecture de tous les enregistrements", exception);
        }
    */
        return null;
    }
}
