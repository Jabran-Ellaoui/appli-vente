package dataAccess;

import dataAccess.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchDAO implements SearchDAOInterface {

    private final Connection connection;

    public SearchDAO() throws SQLException {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    //Recherche 1
    @Override
    public ArrayList<Object[]> getProductsByModelProvenanceDate(String label, String provenance, Date minReceptionDate) throws SQLException {
        ArrayList<Object[]> results = new ArrayList<>();

        String sql = """
            SELECT
                product.unit_price,
                product.promotion_percentage,
                product_model.label,
                lot.provenance,
                lot.reception_date
            FROM product
            JOIN product_model ON product.model_barcode = product_model.barcode
            JOIN lot ON product_model.provenance = lot.number
            WHERE product_model.label = ?
              AND lot.provenance = ?
              AND lot.reception_date >= ?
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, label);
            statement.setString(2, provenance);
            statement.setDate(3, minReceptionDate);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getDouble("unit_price");
                row[1] = rs.getDouble("promotion_percentage");
                row[2] = rs.getString("label");
                row[3] = rs.getString("provenance");
                row[4] = rs.getDate("reception_date");

                results.add(row);
            }
        }

        return results;
    }

    //Recherche 2
    @Override
    public ArrayList<Object[]> getSoldProducts(int clientId, int employeeId, Date saleDate) throws SQLException {
        ArrayList<Object[]> results = new ArrayList<>();

        String sql = """
    SELECT
        product.id AS product_id,
        product.unit_price,
        product.promotion_percentage,

        product_model.label,

        sales_detail.id AS sale_id,
        sales_detail.quantity,
        sales_detail.fidelity_points_used,
        sales_detail.payment_method,
        sales_detail.comment,
        sales_detail.date,

        customer.firstname AS customer_firstname,
        customer.lastname AS customer_lastname,
        customer.id AS customer_id,

        employee.firstname AS employee_firstname,
        employee.lastname AS employee_lastname,
        employee.id AS employee_id

    FROM product
    JOIN product_model ON product.model_barcode = product_model.barcode
    JOIN sales_detail ON product.sale = sales_detail.id
    JOIN customer ON sales_detail.buyer_id = customer.id
    JOIN employee ON sales_detail.seller_id = employee.id
    WHERE customer.id = ?
      AND employee.id = ?
      AND sales_detail.date = ?
""";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            statement.setInt(2, employeeId);
            statement.setDate(3, saleDate);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[16];
                row[0] = rs.getInt("product_id");
                row[1] = rs.getDouble("unit_price");
                row[2] = rs.getDouble("promotion_percentage");
                row[3] = rs.getString("label");

                row[4] = rs.getInt("sale_id");
                row[5] = rs.getInt("quantity");
                row[6] = rs.getBoolean("fidelity_points_used");
                row[7] = rs.getString("payment_method");
                row[8] = rs.getString("comment");
                row[9] = rs.getDate("date");

                row[10] = rs.getString("customer_firstname");
                row[11] = rs.getString("customer_lastname");
                row[12] = rs.getInt("customer_id");

                row[13] = rs.getString("employee_firstname");
                row[14] = rs.getString("employee_lastname");
                row[15] = rs.getInt("employee_id");

                results.add(row);
            }
        }
        return results;
    }

    //Recherche 3
    @Override
    public ArrayList<Object[]> getSalesSummary(int clientId, Date startDate, Date endDate) throws SQLException
    {
        ArrayList<Object[]> results = new ArrayList<>();

        String sql = """
                SELECT
                    customer.firstname,
                    customer.lastname,
                    SUM(sales_detail.quantity) AS total_products,
                    SUM(sales_detail.quantity * product.unit_price) AS total_spent
                FROM customer
                JOIN sales_detail ON customer.id = sales_detail.buyer_id
                JOIN product ON sales_detail.id = product.sale
                WHERE customer.id = ?
                  AND sales_detail.date BETWEEN ? AND ?
                GROUP BY customer.id, customer.firstname, customer.lastname
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, clientId);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[4];
                row[0] = rs.getString("firstname");
                row[1] = rs.getString("lastname");
                row[2] = rs.getInt("total_products");
                row[3] = rs.getDouble("total_spent");

                results.add(row);
            }
        }

        return results;
    }

}