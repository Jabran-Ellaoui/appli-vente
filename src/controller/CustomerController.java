package controller;

import businessLogic.CustomerService;
import exception.ConnectionException;
import exception.CustomerException;
import model.Customer;

import java.util.ArrayList;

public class CustomerController
{
    private final CustomerService customerService;

    public CustomerController() throws ConnectionException
    {
        this.customerService = new CustomerService();
    }

    public ArrayList<Customer> getAllCustomers()
    {

        return customerService.getAllCustomer();

    }
}
