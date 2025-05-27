package main.dataAccess;

import main.exception.EmployeeException;
import main.model.Employee;

import java.util.ArrayList;

public interface EmployeeDAOInterface
{
    ArrayList<Employee> readAll() throws EmployeeException;
}

