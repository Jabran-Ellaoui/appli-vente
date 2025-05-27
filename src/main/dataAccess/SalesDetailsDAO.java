package main.dataAccess;

import main.exception.SalesDetailsException;
import main.exception.ConnectionException;
import main.model.Customer;
import main.model.Employee;
import main.model.SalesDetails;


import java.sql.*;
import java.util.ArrayList;

public class SalesDetailsDAO implements SalesDetailsDAOInterface {
    private final Connection connection;

    public SalesDetailsDAO() throws ConnectionException {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException exception) {
            throw new ConnectionException("Erreur de connexion", exception);
        }
    }

    public void create(SalesDetails salesDetails) throws SalesDetailsException {
        String sql = "INSERT INTO sales_detail (id, quantity, fidelity_points_used, payment_method, comment, date, buyer_id, seller_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, salesDetails.getId());
            preparedStatement.setInt(2, salesDetails.getQuantity());
            preparedStatement.setBoolean(3, salesDetails.isFidelityPointsUsed());

            // Champs facultatifs
            if (salesDetails.getPaymentMethod() != null)
                preparedStatement.setString(4, salesDetails.getPaymentMethod());
            else
                preparedStatement.setNull(4, Types.VARCHAR);

            if (salesDetails.getComment() != null)
                preparedStatement.setString(5, salesDetails.getComment());
            else
                preparedStatement.setNull(5, Types.VARCHAR);

            preparedStatement.setDate(6, Date.valueOf(salesDetails.getDate()));

            if (salesDetails.getBuyer() != null)
                preparedStatement.setInt(7, salesDetails.getBuyer().getId());
            else
                preparedStatement.setNull(7, Types.INTEGER);

            if (salesDetails.getSeller() != null)
                preparedStatement.setInt(8, salesDetails.getSeller().getId());
            else
                preparedStatement.setNull(8, Types.INTEGER);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SalesDetailsException("Erreur lors de la création du détail de vente : ");
        }
    }


    @Override
    public void delete(int id) throws SalesDetailsException
    {
        String sql = "DELETE FROM sales_detail WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException exception) {
            throw new SalesDetailsException("Erreur lors de la suppression");
        }
    }

    public SalesDetails read(int id) throws SalesDetailsException
    {

        String sqlInstruction = "SELECT id, quantity, fidelity_points_used, date, payment_method, comment, buyer_id, seller_id FROM sales_detail WHERE id = ?";
        SalesDetails salesDetails;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction))
        {
            preparedStatement.setInt(1, id);
            try (ResultSet data = preparedStatement.executeQuery())
            {
                if (!data.next())
                {
                    throw new SalesDetailsException("Aucune vente trouvée pour l'ID" + id);
                }
                Customer buyer;
                String paymentMethod;
                String comment;

                Employee seller = new Employee(data.getInt("seller_id"));
                salesDetails = new SalesDetails(data.getInt("id"), data.getInt("quantity"), data.getBoolean("fidelity_points_used"), data.getDate("date").toLocalDate(), seller);

                paymentMethod = data.getString("payment_method");
                if (!data.wasNull()) {salesDetails.setPaymentMethod(paymentMethod);}

                comment = data.getString("comment");
                if (!data.wasNull()) {salesDetails.setComment(comment);}

                buyer = new Customer(data.getInt("buyer_id"));
                if (!data.wasNull()) {salesDetails.setBuyer(buyer);}

                return salesDetails;
            }
        }
        catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur lors de la lecture des détails de vente");
        }
    }

    public void update(SalesDetails salesDetails) throws SalesDetailsException
    {
        String sql = "UPDATE sales_detail SET quantity = ?, fidelity_points_used = ?, payment_method = ?, comment = ?, date = ?, buyer_id = ?, seller_id = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setInt(1, salesDetails.getQuantity());
            preparedStatement.setBoolean(2, salesDetails.isFidelityPointsUsed());
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
            throw new SalesDetailsException("Erreur lors de la mise à jour du détail de vente");
        }
    }

    @Override
    public ArrayList<SalesDetails> readAll() throws SalesDetailsException
    {
        ArrayList<SalesDetails> detailsList = new ArrayList<>();
        String sqlInstruction = "SELECT id, quantity, fidelity_points_used, date, payment_method, comment, buyer_id, seller_id FROM sales_detail";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlInstruction); ResultSet data = preparedStatement.executeQuery())
        {
            Customer buyer;
            String paymentMethod;
            String comment;
            SalesDetails salesDetails;
            while (data.next())
            {

                Employee seller = new Employee(data.getInt("seller_id"));
                salesDetails = new SalesDetails(data.getInt("id"), data.getInt("quantity"), data.getBoolean("fidelity_points_used"), data.getDate("date").toLocalDate(), seller);

                paymentMethod = data.getString("payment_method");
                if (!data.wasNull()) {salesDetails.setPaymentMethod(paymentMethod);}

                comment = data.getString("comment");
                if (!data.wasNull()) {salesDetails.setComment(comment);}

                buyer = new Customer(data.getInt("buyer_id"));
                if (!data.wasNull()) {salesDetails.setBuyer(buyer);}

                detailsList.add(salesDetails);
            }
            return detailsList;
        }
        catch (SQLException exception)
        {
            throw new SalesDetailsException("Erreur lors de la lecture de tous les enregistrements des détails de vente");
        }
    }
}
