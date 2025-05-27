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
