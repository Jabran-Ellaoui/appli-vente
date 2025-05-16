package businessLogic;

import dataAccess.SalesDetailsDAO;
import exception.ConnectionException;
import model.SalesDetails;

import java.util.ArrayList;

public class SalesDetailsService {
    private final SalesDetailsDAO salesDetailsDAO;

    public SalesDetailsService(SalesDetailsDAO salesDetailsDAO) {
        this.salesDetailsDAO = salesDetailsDAO;
    }

    public SalesDetails getSalesDetails(int id) throws ConnectionException {
        return salesDetailsDAO.read(id);
    }

    public ArrayList<SalesDetails> getAllSalesDetails() throws ConnectionException {
        return (ArrayList<SalesDetails>)salesDetailsDAO.readAll();
    }

    // à finir
    public void addNewSalesDetails(SalesDetails salesDetails) throws ConnectionException {
        salesDetailsDAO.create(salesDetails);
    }

    public void updateSalesDetails(SalesDetails salesDetails) throws ConnectionException {
        salesDetailsDAO.update(salesDetails);
    }

    public void deleteSalesDetails(int id) throws ConnectionException {
        salesDetailsDAO.delete(id);
    }
}
