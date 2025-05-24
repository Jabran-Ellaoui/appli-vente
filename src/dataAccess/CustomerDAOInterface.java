package dataAccess;

import exception.CustomerException;
import model.Customer;

import java.util.ArrayList;

public interface CustomerDAOInterface
{
    ArrayList<Customer> readAll() throws CustomerException;
}
