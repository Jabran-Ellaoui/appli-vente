package main.controller;

import main.businessLogic.CustomerService;
import main.exception.ConnectionException;
import main.exception.CustomerException;
import main.model.Customer;

import java.util.ArrayList;

public class CustomerController
{
    private final CustomerService customerService;

    public CustomerController() throws ConnectionException
    {
        this.customerService = new CustomerService();
    }

    public ArrayList<Customer> getAllCustomers() throws CustomerException {

        return customerService.getAllCustomer();

    }

    public Customer getCustomerById(int id) throws CustomerException
    {
        return customerService.getCustomerById(id);
    }
}
