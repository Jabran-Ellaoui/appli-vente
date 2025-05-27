package main.controller;

import main.businessLogic.CustomerService;
import main.exception.ConnectionException;
import main.model.Customer;

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
