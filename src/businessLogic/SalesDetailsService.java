package businessLogic;

import dataAccess.SalesDetailsDAOInterface;
import exception.SalesDetailsException;
import model.SalesDetails;

import java.util.ArrayList;

public class SalesDetailsService {
    private final SalesDetailsDAOInterface salesDetailsDAO;

    public SalesDetailsService(SalesDetailsDAOInterface salesDetailsDAO) throws SalesDetailsException {
        this.salesDetailsDAO = salesDetailsDAO;
    }

    public SalesDetails getSalesDetails(int id) throws SalesDetailsException {
        return salesDetailsDAO.read(id);
    }

    public ArrayList<SalesDetails> getAllSalesDetails() throws SalesDetailsException {
        return (ArrayList<SalesDetails>)salesDetailsDAO.readAll();
    }

    // à finir
    public void addNewSalesDetails(SalesDetails salesDetails) throws SalesDetailsException {
        salesDetailsDAO.create(salesDetails);
    }

    public void updateSalesDetails(SalesDetails salesDetails) throws SalesDetailsException {
        salesDetailsDAO.update(salesDetails);
    }

    public void deleteSalesDetails(int id) throws SalesDetailsException {
        salesDetailsDAO.delete(id);
    }
}
