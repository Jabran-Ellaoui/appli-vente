package main.controller;

import main.businessLogic.EmployeeService;
import main.exception.ConnectionException;
import main.model.Employee;

import java.util.ArrayList;

public class EmployeeController
{
    private final EmployeeService employeeService;

    public EmployeeController() throws ConnectionException
    {
        this.employeeService = new EmployeeService();
    }

    public ArrayList<Employee> getAllEmployee()
    {

            return employeeService.getAllEmployee();

    }
}
