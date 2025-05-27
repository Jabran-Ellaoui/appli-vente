package main.dataAccess;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public interface SearchDAOInterface {

    ArrayList<Object[]> getProductsByModelProvenanceDate(String label, String provenance, Date minReceptionDate) throws SQLException;
    ArrayList<Object[]> getSoldProducts(int clientId, int employeeId, Date saleDate) throws SQLException;
    ArrayList<Object[]> getSalesSummary(int clientId, Date startDate, Date endDate) throws SQLException;

}