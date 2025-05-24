package controller;

import businessLogic.EmployeeService;
import exception.ConnectionException;
import exception.EmployeeException;
import model.Employee;

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
