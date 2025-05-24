package businessLogic;

import dataAccess.EmployeeDAO;
import dataAccess.EmployeeDAOInterface;
import exception.ConnectionException;
import exception.EmployeeException;
import model.Employee;

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
}
