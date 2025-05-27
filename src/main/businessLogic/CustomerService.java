package main.businessLogic;

import main.dataAccess.CustomerDAO;
import main.dataAccess.CustomerDAOInterface;
import main.exception.ConnectionException;
import main.exception.CustomerException;
import main.model.Customer;

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
