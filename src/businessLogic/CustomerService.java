package businessLogic;

import dataAccess.CustomerDAO;
import dataAccess.CustomerDAOInterface;
import exception.ConnectionException;
import exception.CustomerException;
import model.Customer;

import java.util.ArrayList;

public class CustomerService
{
    private final CustomerDAOInterface customerDAO;

    public CustomerService() throws ConnectionException
    {
        this.customerDAO = new CustomerDAO();
    }

    public ArrayList<Customer> getAllCustomer() throws CustomerException
    {
        return (ArrayList<Customer>)customerDAO.readAll();
    }
}
