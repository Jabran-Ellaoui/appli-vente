package main.businessLogic;

import main.dataAccess.SalesDetailsDAO;
import main.dataAccess.SalesDetailsDAOInterface;
import main.exception.ConnectionException;
import main.exception.SalesDetailsException;
import main.model.SalesDetails;

import java.util.ArrayList;

public class SalesDetailsService {
    private final SalesDetailsDAOInterface salesDetailsDAO;

    public SalesDetailsService() throws ConnectionException {
        this.salesDetailsDAO = new SalesDetailsDAO();
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
