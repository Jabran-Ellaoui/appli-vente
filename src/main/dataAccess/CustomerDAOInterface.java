package main.dataAccess;

import main.exception.CustomerException;
import main.model.Customer;

import java.util.ArrayList;

public interface CustomerDAOInterface
{
    ArrayList<Customer> readAll() throws CustomerException;
    Customer getCustomerById(int id) throws CustomerException;
}
