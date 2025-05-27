package main.businessLogic;

import main.dataAccess.SearchDAO;
import main.dataAccess.SearchDAOInterface;
import main.exception.SearchException;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class SearchService {

    private final SearchDAOInterface searchDAO;

    public SearchService() throws SearchException {
        try {
            this.searchDAO = new SearchDAO();
        } catch (SQLException sqlException) {
            throw new SearchException("Erreur lors de la connexion à la base de données", sqlException);
        }
    }

    public ArrayList<Object[]> getProductsByModelProvenanceDate(String label, String provenance, Date minReceptionDate) throws SearchException {
        try {
            return searchDAO.getProductsByModelProvenanceDate(label, provenance, minReceptionDate);
        } catch (SQLException sqlException) {
            throw new SearchException("Erreur lors de la recherche des produits par modèle et provenance", sqlException);
        }
    }

    public ArrayList<Object[]> getSoldProducts(int clientId, int employeeId, Date saleDate) throws SearchException {
        try {
            return searchDAO.getSoldProducts(clientId, employeeId, saleDate);
        } catch (SQLException sqlException) {
            throw new SearchException("Erreur lors de la recherche des produits vendus", sqlException);
        }
    }

    public ArrayList<Object[]> getSalesSummary(int clientId, Date startDate, Date endDate) throws SearchException {
        try {
            return searchDAO.getSalesSummary(clientId, startDate, endDate);
        } catch (SQLException sqlException) {
            throw new SearchException("Erreur lors du calcul du résumé des ventes", sqlException);
        }
    }
}

/*
Script SQL Recherche 1

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

Script SQL Recherche 2

SELECT
    product.unit_price,
    product.promotion_percentage,
    product_model.label,
    sales_detail.quantity,
    sales_detail.fidelity_points_used,
    customer.firstname,
    customer.lastname,
    customer.id,
    employee.firstname,
    employee.lastname,
    employee.id
FROM product
JOIN product_model ON product.model_barcode = product_model.barcode
JOIN sales_detail ON product.sale = sales_detail.id
JOIN customer ON sales_detail.buyer_id = customer.id
JOIN employee ON sales_detail.seller_id = employee.id
WHERE customer.id = ?
  AND employee.id = ?
  AND sales_detail.date = ?


Script SQL Recherche 3

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
GROUP BY customer.firstname, customer.lastname

 */