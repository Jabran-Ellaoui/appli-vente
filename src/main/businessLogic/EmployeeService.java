package main.businessLogic;

import main.dataAccess.EmployeeDAO;
import main.dataAccess.EmployeeDAOInterface;
import main.exception.ConnectionException;
import main.exception.EmployeeException;
import main.model.Employee;

import java.util.ArrayList;

public class EmployeeService
{
    private final EmployeeDAOInterface employeeDAO;

    public EmployeeService() throws ConnectionException
    {
        this.employeeDAO = new EmployeeDAO();
    }

    public ArrayList<Employee> getAllEmployee() throws EmployeeException
    {
        return (ArrayList<Employee>)employeeDAO.readAll();
    }

    public Employee getEmployeeById(int id) throws EmployeeException
    {
        return employeeDAO.getEmployeeById(id);
    }
}
